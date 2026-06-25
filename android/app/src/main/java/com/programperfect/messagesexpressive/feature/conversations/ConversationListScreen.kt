@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package com.programperfect.messagesexpressive.feature.conversations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.programperfect.messagesexpressive.core.model.demoConversations

@Composable
fun ConversationListScreen(
    modifier: Modifier = Modifier,
    selectedId: String?,
    onConversationSelected: (String) -> Unit
) {
    var activeTab by rememberSaveable { mutableStateOf("all") }
    val filtered = remember(activeTab) {
        demoConversations.filter { item ->
            when (activeTab) {
                "pinned" -> item.pinned
                "unread" -> item.unreadCount > 0
                else -> true
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 18.dp),
        contentPadding = PaddingValues(top = 22.dp, bottom = 126.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { PixelHeader() }
        item { SceneHeroCard() }
        item { SearchPill() }
        item { ExpressiveFilterGroup(activeTab = activeTab, onTabChange = { activeTab = it }) }
        items(filtered, key = { it.id }) { item ->
            ConversationCard(
                item = item,
                selected = selectedId == item.id,
                onClick = { onConversationSelected(item.id) }
            )
        }
    }
}

@Composable
private fun PixelHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Messages", color = Ink, style = MaterialTheme.typography.displayLarge)
            Text("Native Pixel Expressive shell", color = Ink.copy(alpha = 0.60f), style = MaterialTheme.typography.labelLarge)
        }
        Surface(
            modifier = Modifier.size(52.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.74f),
            tonalElevation = 6.dp,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                LoadingIndicator(modifier = Modifier.size(27.dp), color = Violet)
            }
        }
    }
}

@Composable
private fun SceneHeroCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(188.dp),
        shape = RoundedCornerShape(44.dp),
        color = Night,
        tonalElevation = 10.dp,
        shadowElevation = 12.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(Night, Violet, Coral)))
                .padding(20.dp)
        ) {
            Text(
                "NICE",
                color = Color.White.copy(alpha = 0.90f),
                fontSize = 76.sp,
                lineHeight = 66.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-5).sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Lime,
                modifier = Modifier.align(Alignment.TopEnd).offset(y = 6.dp)
            ) {
                Text(
                    "4x faster",
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 9.dp),
                    color = Ink,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Text(
                "Google Pixel mood, Compose-native",
                modifier = Modifier.align(Alignment.BottomStart),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(72.dp),
                shape = RoundedCornerShape(32.dp, 18.dp, 36.dp, 22.dp),
                color = Lilac
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Rounded.Chat, null, tint = Ink, modifier = Modifier.size(30.dp))
                }
            }
        }
    }
}

@Composable
private fun SearchPill() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(36.dp),
        color = Color.White.copy(alpha = 0.86f),
        tonalElevation = 5.dp,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Search, null, tint = Ink.copy(alpha = 0.74f))
            Spacer(Modifier.width(12.dp))
            Text("Search conversations", color = Ink.copy(alpha = 0.62f), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun ExpressiveFilterGroup(activeTab: String, onTabChange: (String) -> Unit) {
    ButtonGroup(
        overflowIndicator = { menuState -> ButtonGroupDefaults.OverflowIndicator(menuState) },
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        toggleableItem(
            checked = activeTab == "all",
            label = "All",
            onCheckedChange = { if (it) onTabChange("all") },
            icon = { Icon(Icons.Rounded.Inbox, null, modifier = Modifier.size(18.dp)) },
            weight = 1f
        )
        toggleableItem(
            checked = activeTab == "pinned",
            label = "Pinned",
            onCheckedChange = { if (it) onTabChange("pinned") },
            icon = { Icon(Icons.Rounded.Star, null, modifier = Modifier.size(18.dp)) },
            weight = 1f
        )
        toggleableItem(
            checked = activeTab == "unread",
            label = "Unread",
            onCheckedChange = { if (it) onTabChange("unread") },
            icon = { Icon(Icons.Rounded.Chat, null, modifier = Modifier.size(18.dp)) },
            weight = 1f
        )
    }
}

@Composable
private fun ConversationCard(item: Conversation, selected: Boolean, onClick: () -> Unit) {
    val colors = item.accent.colors()
    val cardShape = if (selected) RoundedCornerShape(42.dp, 28.dp, 42.dp, 24.dp) else RoundedCornerShape(36.dp)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardShape)
            .clickable(onClick = onClick)
            .animateContentSize(),
        shape = cardShape,
        color = if (selected) colors.card else colors.card.copy(alpha = 0.88f),
        tonalElevation = if (selected) 12.dp else 5.dp,
        shadowElevation = if (selected) 8.dp else 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(item)
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.onCard,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    item.lastMessage,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.onCard.copy(alpha = 0.72f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(item.time, color = colors.onCard.copy(alpha = 0.78f), style = MaterialTheme.typography.labelLarge)
                AnimatedVisibility(item.unreadCount > 0) {
                    Surface(shape = CircleShape, color = Ink, modifier = Modifier.padding(top = 6.dp)) {
                        Text(
                            item.unreadCount.toString(),
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Avatar(item: Conversation, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.size(62.dp),
        shape = RoundedCornerShape(28.dp, 20.dp, 32.dp, 22.dp),
        color = item.accent.colors().avatar,
        tonalElevation = 7.dp,
        shadowElevation = 3.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(item.initials, color = Color.White, fontWeight = FontWeight.Black, fontSize = 24.sp)
        }
    }
}

