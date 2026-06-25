@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package com.programperfect.messagesexpressive.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TonalToggleButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.programperfect.messagesexpressive.core.designsystem.Coral
import com.programperfect.messagesexpressive.core.designsystem.Cyan
import com.programperfect.messagesexpressive.core.designsystem.Ink
import com.programperfect.messagesexpressive.core.designsystem.Lilac
import com.programperfect.messagesexpressive.core.designsystem.Lime
import com.programperfect.messagesexpressive.core.designsystem.Night
import com.programperfect.messagesexpressive.core.designsystem.Violet
import com.programperfect.messagesexpressive.core.designsystem.colors
import com.programperfect.messagesexpressive.core.model.Conversation
import com.programperfect.messagesexpressive.core.model.Message
import com.programperfect.messagesexpressive.core.model.demoMessages
import com.programperfect.messagesexpressive.feature.conversations.Avatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(conversation: Conversation, onBack: (() -> Unit)?) {
    var text by rememberSaveable(conversation.id) { mutableStateOf("") }
    val thread = remember(conversation.id) { demoMessages[conversation.id].orEmpty() }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) { Icon(Icons.Rounded.ArrowBack, "Back", tint = Ink) }
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Avatar(conversation, modifier = Modifier.size(48.dp))
                        Spacer(Modifier.width(10.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(conversation.name, maxLines = 1, overflow = TextOverflow.Ellipsis, color = Ink, fontWeight = FontWeight.Black)
                            Text("cinematic messenger - online", style = MaterialTheme.typography.labelSmall, color = Violet)
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Rounded.Call, null, tint = Ink) }
                    IconButton(onClick = {}) { Icon(Icons.Rounded.Videocam, null, tint = Ink) }
                    IconButton(onClick = {}) { Icon(Icons.Rounded.MoreVert, null, tint = Ink) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White.copy(alpha = 0.32f))
            )
        },
        bottomBar = { Composer(text = text, onTextChange = { text = it }, onSend = { text = "" }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { ChatBanner(conversation) }
            item { SceneModeStrip() }
            items(thread, key = { it.id }) { msg -> MessageBubble(msg) }
        }
    }
}

@Composable
private fun ChatBanner(conversation: Conversation) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        shape = RoundedCornerShape(42.dp),
        color = Night,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(conversation.accent.colors().avatar, Cyan, Lilac)))
                .padding(18.dp)
        ) {
            Text(
                conversation.name.uppercase(),
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 48.sp,
                lineHeight = 42.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-2.5).sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Surface(shape = RoundedCornerShape(26.dp), color = Color.White.copy(alpha = 0.76f), modifier = Modifier.align(Alignment.BottomEnd)) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LoadingIndicator(modifier = Modifier.size(18.dp), color = Violet)
                    Text("scene chat", color = Ink, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
private fun SceneModeStrip() {
    var selected by rememberSaveable { mutableStateOf("going") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TonalToggleButton(checked = selected == "going", onCheckedChange = { if (it) selected = "going" }) {
            Icon(Icons.Rounded.Done, null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Going")
        }
        TonalToggleButton(checked = selected == "maybe", onCheckedChange = { if (it) selected = "maybe" }) {
            Icon(Icons.Rounded.Star, null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Maybe")
        }
        TonalToggleButton(checked = selected == "quiet", onCheckedChange = { if (it) selected = "quiet" }) {
            Icon(Icons.Rounded.Archive, null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Quiet")
        }
    }
}

@Composable
private fun MessageBubble(message: Message) {
    if (message.sticker != null) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (message.outgoing) Arrangement.End else Arrangement.Start) {
            Text(
                message.sticker,
                color = Cyan,
                fontSize = 76.sp,
                lineHeight = 70.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-4).sp,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
            )
        }
        return
    }

    val outgoing = message.outgoing
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (outgoing) Arrangement.End else Arrangement.Start) {
        Surface(
            modifier = Modifier.widthIn(max = 322.dp),
            color = if (outgoing) Cyan else Lilac.copy(alpha = 0.96f),
            contentColor = if (outgoing) Color(0xFF001C25) else Ink,
            tonalElevation = 4.dp,
            shadowElevation = 2.dp,
            shape = RoundedCornerShape(
                topStart = if (outgoing) 30.dp else 12.dp,
                topEnd = if (outgoing) 12.dp else 30.dp,
                bottomEnd = if (outgoing) 8.dp else 30.dp,
                bottomStart = if (outgoing) 30.dp else 8.dp
            )
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp)) {
                Text(message.text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(message.time, style = MaterialTheme.typography.labelSmall, color = Ink.copy(alpha = 0.58f))
                    if (outgoing) {
                        Icon(
                            if (message.read) Icons.Rounded.DoneAll else Icons.Rounded.Done,
                            null,
                            modifier = Modifier.size(14.dp),
                            tint = Ink.copy(alpha = 0.74f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Composer(text: String, onTextChange: (String) -> Unit, onSend: () -> Unit) {
    var toolsExpanded by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(42.dp),
        color = Color.White.copy(alpha = 0.90f),
        tonalElevation = 10.dp,
        shadowElevation = 10.dp
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(34.dp),
                color = Cyan.copy(alpha = 0.20f),
                contentColor = Ink
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledIconButton(
                        onClick = {},
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.76f),
                            contentColor = Ink
                        )
                    ) {
                        Icon(Icons.Rounded.Add, "Attach")
                    }
                    BasicTextField(
                        value = text,
                        onValueChange = onTextChange,
                        modifier = Modifier.weight(1f),
                        maxLines = 4,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Ink, fontWeight = FontWeight.SemiBold),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.CenterStart) {
                                if (text.isBlank()) {
                                    Text("Message", color = Ink.copy(alpha = 0.48f), style = MaterialTheme.typography.bodyLarge)
                                }
                                innerTextField()
                            }
                        }
                    )
                    IconButton(onClick = { toolsExpanded = !toolsExpanded }) {
                        Icon(Icons.Rounded.MoreVert, "More tools", tint = Ink)
                    }
                }
            }

            HorizontalFloatingToolbar(
                expanded = text.isBlank() || toolsExpanded,
                floatingActionButton = {
                    FloatingToolbarDefaults.VibrantFloatingActionButton(
                        onClick = {
                            if (text.isBlank()) toolsExpanded = !toolsExpanded else onSend()
                        },
                        shape = RoundedCornerShape(26.dp),
                        containerColor = if (text.isBlank()) Coral else Lime,
                        contentColor = Ink
                    ) {
                        Icon(if (text.isBlank()) Icons.Rounded.Add else Icons.Rounded.Send, if (text.isBlank()) "Toggle tools" else "Send")
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(
                    toolbarContainerColor = Night.copy(alpha = 0.96f),
                    toolbarContentColor = Lilac,
                    fabContainerColor = if (text.isBlank()) Coral else Lime,
                    fabContentColor = Ink
                ),
                shape = RoundedCornerShape(32.dp)
            ) {
                IconButton(onClick = {}) { Icon(Icons.Rounded.Star, "Star") }
                IconButton(onClick = {}) { Icon(Icons.Rounded.Search, "Search") }
                IconButton(onClick = {}) { Icon(Icons.Rounded.Archive, "Archive") }
                IconButton(onClick = {}) { Icon(Icons.Rounded.MoreVert, "More") }
            }
        }
    }
}

@Composable
fun EmptyChatState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(shape = RoundedCornerShape(56.dp), color = Night, tonalElevation = 8.dp, modifier = Modifier.size(248.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LoadingIndicator(modifier = Modifier.size(58.dp), color = Lilac)
                    Text("Choose a scene chat", fontWeight = FontWeight.Black, color = Color.White, modifier = Modifier.padding(top = 14.dp))
                }
            }
        }
    }
}

