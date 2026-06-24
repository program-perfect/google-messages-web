@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package com.programperfect.messagesexpressive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpressiveTheme {
                MessengerApp()
            }
        }
    }
}

private val Violet = Color(0xFF7B2CFF)
private val ElectricViolet = Color(0xFFB85CFF)
private val Lilac = Color(0xFFD9B8FF)
private val SoftLilac = Color(0xFFF1E6FF)
private val Coral = Color(0xFFFF7D8A)
private val Cyan = Color(0xFF00A8E8)
private val Lime = Color(0xFFD8FF6A)
private val Ink = Color(0xFF241126)
private val Night = Color(0xFF19051F)
private val Frost = Color(0xFFFFF7FF)

private val ExpressiveScheme = lightColorScheme(
    primary = Violet,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEBDDFF),
    onPrimaryContainer = Ink,
    secondary = Color(0xFF725C85),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF1D9FF),
    onSecondaryContainer = Ink,
    tertiary = Coral,
    onTertiary = Color(0xFF33000A),
    tertiaryContainer = Color(0xFFFFD8DE),
    onTertiaryContainer = Color(0xFF3E0012),
    background = Color(0xFFFFF4FF),
    onBackground = Ink,
    surface = Frost,
    onSurface = Ink,
    surfaceVariant = Color(0xFFF1E4F8),
    onSurfaceVariant = Color(0xFF56405F),
    outline = Color(0xFF7B687F)
)

private val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(12.dp),
    small = RoundedCornerShape(20.dp),
    medium = RoundedCornerShape(30.dp),
    large = RoundedCornerShape(40.dp),
    extraLarge = RoundedCornerShape(54.dp)
)

private val ExpressiveTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 62.sp,
        lineHeight = 58.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = (-3).sp
    ),
    headlineLarge = TextStyle(
        fontSize = 36.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = (-1.4).sp
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = (-0.45).sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Medium
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.15.sp
    )
)

@Composable
private fun ExpressiveTheme(content: @Composable () -> Unit) {
    MaterialExpressiveTheme(
        colorScheme = ExpressiveScheme,
        motionScheme = MotionScheme.expressive(),
        shapes = ExpressiveShapes,
        typography = ExpressiveTypography,
        content = content
    )
}

@Composable
private fun MessengerApp() {
    val isWide = LocalConfiguration.current.screenWidthDp >= 720
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    val currentId = selectedId ?: if (isWide) conversations.first().id else null
    val selectedConversation = conversations.firstOrNull { it.id == currentId }

    ExpressiveBackdrop {
        if (isWide) {
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
                    ConversationList(selectedId = currentId, onSelect = { selectedId = it })
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
                    if (selectedConversation == null) {
                        EmptyChatState()
                    } else {
                        ChatScreen(item = selectedConversation, onBack = null)
                    }
                }
            }
        } else {
            if (selectedConversation == null) {
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
                    ConversationList(
                        modifier = Modifier.padding(padding),
                        selectedId = null,
                        onSelect = { selectedId = it }
                    )
                }
            } else {
                ChatScreen(item = selectedConversation, onBack = { selectedId = null })
            }
        }
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
        content()
    }
}
@Composable
private fun PixelRail() {
    val railState = rememberWideNavigationRailState(initialValue = WideNavigationRailValue.Expanded)
    val itemColors = WideNavigationRailItemDefaults.colors(
        selectedIconColor = Ink,
        selectedTextColor = Ink,
        selectedIndicatorColor = Lilac,
        unselectedIconColor = Lilac,
        unselectedTextColor = Lilac.copy(alpha = 0.84f)
    )

    WideNavigationRail(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxHeight()
            .widthIn(min = 188.dp, max = 214.dp),
        state = railState,
        shape = RoundedCornerShape(42.dp),
        colors = WideNavigationRailDefaults.colors(
            containerColor = Night.copy(alpha = 0.94f),
            contentColor = Lilac
        ),
        arrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        header = {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 18.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                color = Coral,
                contentColor = Ink
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Rounded.Edit, null)
                    Text("Compose", fontWeight = FontWeight.Black)
                }
            }
        }
    ) {
        WideNavigationRailItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Rounded.Chat, null) },
            label = { Text("Chats") },
            railExpanded = true,
            iconPosition = NavigationItemIconPosition.Start,
            colors = itemColors
        )
        WideNavigationRailItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Rounded.Archive, null) },
            label = { Text("Archive") },
            railExpanded = true,
            iconPosition = NavigationItemIconPosition.Start,
            colors = itemColors
        )
        WideNavigationRailItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Rounded.Settings, null) },
            label = { Text("Setup") },
            railExpanded = true,
            iconPosition = NavigationItemIconPosition.Start,
            colors = itemColors
        )
    }
}

@Composable
private fun PixelBottomBar() {
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        shape = RoundedCornerShape(38.dp),
        color = Color.White.copy(alpha = 0.86f),
        tonalElevation = 6.dp,
        shadowElevation = 6.dp
    ) {
        NavigationBar(containerColor = Color.Transparent, tonalElevation = 0.dp) {
            NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Rounded.Inbox, null) }, label = { Text("Chats") })
            NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.Archive, null) }, label = { Text("Archive") })
            NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.Settings, null) }, label = { Text("Setup") })
        }
    }
}

@Composable
private fun ConversationList(
    modifier: Modifier = Modifier,
    selectedId: String?,
    onSelect: (String) -> Unit
) {
    var activeTab by rememberSaveable { mutableStateOf("all") }
    val filtered = remember(activeTab) {
        conversations.filter { item ->
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
            ConversationCard(item = item, selected = selectedId == item.id, onClick = { onSelect(item.id) })
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
        Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
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
private fun ConversationCard(item: ConversationUi, selected: Boolean, onClick: () -> Unit) {
    val cardShape = if (selected) RoundedCornerShape(42.dp, 28.dp, 42.dp, 24.dp) else RoundedCornerShape(36.dp)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardShape)
            .clickable(onClick = onClick)
            .animateContentSize(),
        shape = cardShape,
        color = if (selected) item.cardColor else item.cardColor.copy(alpha = 0.88f),
        tonalElevation = if (selected) 12.dp else 5.dp,
        shadowElevation = if (selected) 8.dp else 2.dp
    ) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp), verticalAlignment = Alignment.CenterVertically) {
            Avatar(item)
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = item.onCardColor,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    item.lastMessage,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = item.onCardColor.copy(alpha = 0.72f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(item.time, color = item.onCardColor.copy(alpha = 0.78f), style = MaterialTheme.typography.labelLarge)
                AnimatedVisibility(item.unreadCount > 0) {
                    Surface(shape = CircleShape, color = Ink, modifier = Modifier.padding(top = 6.dp)) {
                        Text(item.unreadCount.toString(), modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp), color = Color.White, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun Avatar(item: ConversationUi) {
    Surface(
        modifier = Modifier.size(62.dp),
        shape = RoundedCornerShape(28.dp, 20.dp, 32.dp, 22.dp),
        color = item.avatarColor,
        tonalElevation = 7.dp,
        shadowElevation = 3.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(item.initials, color = Color.White, fontWeight = FontWeight.Black, fontSize = 24.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(item: ConversationUi, onBack: (() -> Unit)?) {
    var text by rememberSaveable(item.id) { mutableStateOf("") }
    val thread = remember(item.id) { messages[item.id].orEmpty() }

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
                        Avatar(item)
                        Spacer(Modifier.width(10.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(item.name, maxLines = 1, overflow = TextOverflow.Ellipsis, color = Ink, fontWeight = FontWeight.Black)
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
            item { ChatBanner(item) }
            item { SceneModeStrip() }
            items(thread, key = { it.id }) { msg -> MessageBubble(msg) }
        }
    }
}

@Composable
private fun ChatBanner(item: ConversationUi) {
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
                .background(Brush.linearGradient(listOf(item.avatarColor, Cyan, Lilac)))
                .padding(18.dp)
        ) {
            Text(
                item.name.uppercase(),
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
private fun MessageBubble(msg: MessageUi) {
    if (msg.sticker != null) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (msg.outgoing) Arrangement.End else Arrangement.Start) {
            Text(
                msg.sticker,
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

    val outgoing = msg.outgoing
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
                Text(msg.text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Row(modifier = Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(msg.time, style = MaterialTheme.typography.labelSmall, color = Ink.copy(alpha = 0.58f))
                    if (outgoing) Icon(if (msg.read) Icons.Rounded.DoneAll else Icons.Rounded.Done, null, modifier = Modifier.size(14.dp), tint = Ink.copy(alpha = 0.74f))
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
                    FilledIconButton(onClick = {}, colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color.White.copy(alpha = 0.76f), contentColor = Ink)) {
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
private fun EmptyChatState() {
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

data class ConversationUi(
    val id: String,
    val name: String,
    val initials: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0,
    val pinned: Boolean = false,
    val avatarColor: Color,
    val cardColor: Color,
    val onCardColor: Color
)

data class MessageUi(
    val id: String,
    val text: String,
    val outgoing: Boolean,
    val time: String,
    val read: Boolean = true,
    val sticker: String? = null
)

private val conversations = listOf(
    ConversationUi("hwan", "Хван", "ХВ", "Маршрут Грушина пришлю отдельной картой.", "15:42", 2, true, Violet, Lilac, Ink),
    ConversationUi("nikolsky", "Никольский", "АН", "Посмотри документ на планшете.", "14:18", 0, true, Color(0xFF00796B), Lime, Color(0xFF152000)),
    ConversationUi("karaev", "Караев", "СК", "Выйду через служебный вход.", "13:07", 1, false, Color(0xFFC75D4D), Color(0xFFFFB0A5), Color(0xFF350003)),
    ConversationUi("prop", "Реквизит", "РК", "Готовы паспорт, ориентировка и маршрут.", "вчера", 0, false, Color(0xFF7D5260), Color(0xFF2A0B33), Color(0xFFFFE8FF))
)

private val messages = mapOf(
    "hwan" to listOf(
        MessageUi("h1", "Никольский, я скинула тебе карту. Красная точка - конечная.", false, "15:35"),
        MessageUi("h2", "Получил. Это по Грушину?", true, "15:36"),
        MessageUi("h3", "Да. Документ тоже приложила. Не официальный рапорт, а служебная выписка.", false, "15:37"),
        MessageUi("h4", "Понял. В кадре читаю с планшета.", true, "15:38"),
        MessageUi("h5", "", false, "15:39", sticker = "NICE"),
        MessageUi("h6", "Только не называй это делом. Это проверка по линии ведомств.", false, "15:42")
    ),
    "nikolsky" to listOf(
        MessageUi("n1", "Хван прислала таблицу по счетам.", false, "14:12"),
        MessageUi("n2", "Покажи Караеву. Пусть смотрит на связку фамилий.", true, "14:14"),
        MessageUi("n3", "Он уже рядом. Открою на планшете.", false, "14:18")
    ),
    "karaev" to listOf(
        MessageUi("k1", "Ты где?", true, "13:04"),
        MessageUi("k2", "На месте. Машину поставил дальше от выезда.", false, "13:05"),
        MessageUi("k3", "Не геройствуй.", true, "13:06"),
        MessageUi("k4", "Поздно сказал.", false, "13:07", read = false)
    ),
    "prop" to listOf(
        MessageUi("p1", "На сцену нужны маршрут, фото документа и экран СМС.", false, "вчера"),
        MessageUi("p2", "Сделаю в Material-оболочке, чтобы выглядело как настоящее приложение.", true, "вчера")
    )
)
