package com.programperfect.messagesexpressive.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.programperfect.messagesexpressive.core.designsystem.Coral
import com.programperfect.messagesexpressive.core.designsystem.Cyan
import com.programperfect.messagesexpressive.core.designsystem.ElectricViolet
import com.programperfect.messagesexpressive.core.designsystem.Ink
import com.programperfect.messagesexpressive.core.designsystem.Lime
import com.programperfect.messagesexpressive.core.model.demoConversations
import com.programperfect.messagesexpressive.feature.chat.ChatScreen
import com.programperfect.messagesexpressive.feature.chat.EmptyChatState
import com.programperfect.messagesexpressive.feature.conversations.ConversationListScreen
import com.programperfect.messagesexpressive.navigation.PixelBottomBar
import com.programperfect.messagesexpressive.navigation.PixelRail

@Composable
fun MessagesApp() {
    val isWide = LocalConfiguration.current.screenWidthDp >= 720
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    val currentId = selectedId ?: if (isWide) demoConversations.first().id else null
    val selectedConversation = demoConversations.firstOrNull { it.id == currentId }

    ExpressiveBackdrop {
        if (isWide) {
            WideMessagesLayout(
                selectedId = currentId,
                onConversationSelected = { selectedId = it },
                selectedConversationId = selectedConversation?.id
            )
        } else if (selectedConversation == null) {
            CompactConversationLayout(onConversationSelected = { selectedId = it })
        } else {
            ChatScreen(conversation = selectedConversation, onBack = { selectedId = null })
        }
    }
}

@Composable
private fun WideMessagesLayout(
    selectedId: String?,
    selectedConversationId: String?,
    onConversationSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PixelRail()
        Surface(
            modifier = Modifier
                .widthIn(min = 380.dp, max = 440.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(46.dp),
            color = Color.White.copy(alpha = 0.74f),
            tonalElevation = 8.dp,
            shadowElevation = 12.dp
        ) {
            ConversationListScreen(selectedId = selectedId, onConversationSelected = onConversationSelected)
        }
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(46.dp),
            color = Color.White.copy(alpha = 0.76f),
            tonalElevation = 10.dp,
            shadowElevation = 14.dp
        ) {
            val conversation = demoConversations.firstOrNull { it.id == selectedConversationId }
            if (conversation == null) {
                EmptyChatState()
            } else {
                ChatScreen(conversation = conversation, onBack = null)
            }
        }
    }
}

@Composable
private fun CompactConversationLayout(onConversationSelected: (String) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = Coral,
                contentColor = Ink,
                shape = RoundedCornerShape(30.dp, 20.dp, 34.dp, 24.dp),
                modifier = Modifier.size(78.dp)
            ) {
                Icon(Icons.Rounded.Edit, contentDescription = "New chat", modifier = Modifier.size(31.dp))
            }
        },
        bottomBar = { PixelBottomBar() }
    ) { padding ->
        ConversationListScreen(
            modifier = Modifier.padding(padding),
            selectedId = null,
            onConversationSelected = onConversationSelected
        )
    }
}

@Composable
private fun ExpressiveBackdrop(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFECFF), Color(0xFFE4D0FF), Color(0xFFFFF7FF))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Coral.copy(alpha = 0.20f), radius = size.minDimension * 0.46f, center = Offset(size.width * 0.03f, size.height * 0.12f))
            drawCircle(ElectricViolet.copy(alpha = 0.22f), radius = size.minDimension * 0.52f, center = Offset(size.width * 0.98f, size.height * 0.18f))
            drawCircle(Cyan.copy(alpha = 0.13f), radius = size.minDimension * 0.36f, center = Offset(size.width * 0.70f, size.height * 0.86f))
            drawCircle(Lime.copy(alpha = 0.22f), radius = size.minDimension * 0.22f, center = Offset(size.width * 0.13f, size.height * 0.82f))
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            content()
        }
    }
}


