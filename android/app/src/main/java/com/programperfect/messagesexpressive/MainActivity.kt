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
private val Lilac = Color(0xFFD9B8FF)
private val Coral = Color(0xFFFF7D8A)
private val Cyan = Color(0xFF00A8E8)
private val Lime = Color(0xFFD8FF6A)
private val Ink = Color(0xFF241126)
private val Night = Color(0xFF19051F)

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
    surface = Color(0xFFFFF7FF),
    onSurface = Ink,
    surfaceVariant = Color(0xFFF1E4F8),
    onSurfaceVariant = Color(0xFF56405F),
    outline = Color(0xFF7B687F)
)

private val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(18.dp),
    medium = RoundedCornerShape(28.dp),
    large = RoundedCornerShape(38.dp),
    extraLarge = RoundedCornerShape(48.dp)
)

@Composable
private fun ExpressiveTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ExpressiveScheme,
        typography = Typography(),
        shapes = ExpressiveShapes,
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
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PixelRail()
                Surface(
                    modifier = Modifier
                        .width(436.dp)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(42.dp),
                    color = Color.White.copy(alpha = 0.72f),
                    tonalElevation = 8.dp,
                    shadowElevation = 10.dp
                ) {
                    ConversationList(selectedId = currentId, onSelect = { selectedId = it })
                }
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(42.dp),
                    color = Color.White.copy(alpha = 0.74f),
                    tonalElevation = 10.dp,
                    shadowElevation = 12.dp
                ) {
                    if (selectedConversation == null) EmptyChatState() else ChatScreen(selectedConversation, null)
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
                            shape = RoundedCornerShape(30.dp),
                            modifier = Modifier.size(74.dp)
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
                ChatScreen(selectedConversation, onBack = { selectedId = null })
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
                    listOf(Color(0xFFFFE9FF), Color(0xFFE8D4FF), Color(0xFFFFF7FF))
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Coral.copy(alpha = 0.18f), radius = size.minDimension * 0.46f, center = Offset(size.width * 0.05f, size.height * 0.10f))
            drawCircle(Violet.copy(alpha = 0.16f), radius = size.minDimension * 0.48f, center = Offset(size.width * 0.96f, size.height * 0.22f))
            drawCircle(Cyan.copy(alpha = 0.12f), radius = size.minDimension * 0.38f, center = Offset(size.width * 0.70f, size.height * 0.86f))
        }
        content()
    }
}

@Composable
private fun PixelRail() {
    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(36.dp)),
        containerColor = Night.copy(alpha = 0.92f),
        contentColor = Lilac
    ) {
        Spacer(Modifier.height(20.dp))
        NavigationRailItem(selected = true, onClick = {}, icon = { Icon(Icons.Rounded.Chat, null) }, label = { Text("Chats") })
        NavigationRailItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.Archive, null) }, label = { Text("Archive") })
        NavigationRailItem(selected = false, onClick = {}, icon = { Icon(Icons.Rounded.Settings, null) }, label = { Text("Setup") })
    }
}

@Composable
private fun PixelBottomBar() {
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        shape = RoundedCornerShape(36.dp),
        color = Color.White.copy(alpha = 0.80f),
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
        contentPadding = PaddingValues(top = 22.dp, bottom = 118.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { PixelHeader() }
        item { SceneHeroCard() }
        item { SearchPill() }
        item {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip(selected = activeTab == "all", onClick = { activeTab = "all" }, label = { Text("All") })
                FilterChip(
                    selected = activeTab == "pinned",
                    onClick = { activeTab = "pinned" },
                    label = { Text("Pinned") },
                    leadingIcon = { Icon(Icons.Rounded.Star, null, modifier = Modifier.size(18.dp)) }
                )
                FilterChip(selected = activeTab == "unread", onClick = { activeTab = "unread" }, label = { Text("Unread") })
            }
        }
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
            Text(
                "Messages",
                color = Ink,
                fontSize = 48.sp,
                lineHeight = 48.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-2).sp
            )
            Text("Pixel Expressive shell", color = Ink.copy(alpha = 0.58f), style = MaterialTheme.typography.labelLarge)
        }
        Surface(modifier = Modifier.size(46.dp), shape = CircleShape, color = Color.White.copy(alpha = 0.72f), tonalElevation = 4.dp) {
            IconButton(onClick = {}) { Icon(Icons.Rounded.MoreVert, null, tint = Ink) }
        }
    }
}

@Composable
private fun SceneHeroCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp),
        shape = RoundedCornerShape(42.dp),
        color = Night,
        tonalElevation = 10.dp,
        shadowElevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(Night, Violet, Coral)))
                .padding(20.dp)
        ) {
            Text(
                "NICE",
                color = Color.White.copy(alpha = 0.88f),
                fontSize = 70.sp,
                lineHeight = 64.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-4).sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Surface(shape = RoundedCornerShape(28.dp), color = Lime, modifier = Modifier.align(Alignment.TopEnd).offset(y = 6.dp)) {
                Text("scene mode", modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), color = Ink, fontWeight = FontWeight.ExtraBold)
            }
            Text("Messenger imitation for shot", modifier = Modifier.align(Alignment.BottomStart), color = Color.White, fontWeight = FontWeight.SemiBold)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(70.dp),
                shape = RoundedCornerShape(30.dp, 18.dp, 34.dp, 22.dp),
                color = Lilac
            ) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Rounded.Chat, null, tint = Ink) }
            }
        }
    }
}

@Composable
private fun SearchPill() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(34.dp),
        color = Color.White.copy(alpha = 0.80f),
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
private fun ConversationCard(item: ConversationUi, selected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(36.dp))
            .clickable(onClick = onClick)
            .animateContentSize(),
        shape = RoundedCornerShape(36.dp),
        color = if (selected) item.cardColor else item.cardColor.copy(alpha = 0.86f),
        tonalElevation = if (selected) 12.dp else 5.dp,
        shadowElevation = if (selected) 8.dp else 2.dp
    ) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp), verticalAlignment = Alignment.CenterVertically) {
            Avatar(item)
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, maxLines = 1, overflow = TextOverflow.Ellipsis, color = item.onCardColor, fontSize = 22.sp, lineHeight = 24.sp, fontWeight = FontWeight.Black, letterSpacing = (-0.7).sp)
                Text(item.lastMessage, maxLines = 1, overflow = TextOverflow.Ellipsis, color = item.onCardColor.copy(alpha = 0.72f), style = MaterialTheme.typography.bodyLarge)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(item.time, color = item.onCardColor.copy(alpha = 0.78f), fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelLarge)
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
                            Text("cinematic messenger · online", style = MaterialTheme.typography.labelSmall, color = Violet)
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Rounded.Call, null, tint = Ink) }
                    IconButton(onClick = {}) { Icon(Icons.Rounded.Videocam, null, tint = Ink) }
                    IconButton(onClick = {}) { Icon(Icons.Rounded.MoreVert, null, tint = Ink) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White.copy(alpha = 0.30f))
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
            items(thread, key = { it.id }) { msg -> MessageBubble(msg) }
        }
    }
}

@Composable
private fun ChatBanner(item: ConversationUi) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(154.dp),
        shape = RoundedCornerShape(40.dp),
        color = Night,
        tonalElevation = 8.dp,
        shadowElevation = 7.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(item.avatarColor, Cyan, Lilac)))
                .padding(18.dp)
        ) {
            Text(item.name.uppercase(), color = Color.White.copy(alpha = 0.90f), fontSize = 44.sp, lineHeight = 40.sp, fontWeight = FontWeight.Black, letterSpacing = (-2).sp, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.align(Alignment.TopStart))
            Surface(shape = RoundedCornerShape(26.dp), color = Color.White.copy(alpha = 0.72f), modifier = Modifier.align(Alignment.BottomEnd)) {
                Text("scene chat", modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), color = Ink, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun MessageBubble(msg: MessageUi) {
    if (msg.sticker != null) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (msg.outgoing) Arrangement.End else Arrangement.Start) {
            Text(msg.sticker, color = Cyan, fontSize = 72.sp, lineHeight = 70.sp, fontWeight = FontWeight.Black, letterSpacing = (-4).sp, modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp))
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
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(38.dp),
        color = Cyan,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = {}) { Icon(Icons.Rounded.Add, "Attach", tint = Ink) }
            OutlinedTextField(value = text, onValueChange = onTextChange, modifier = Modifier.weight(1f), placeholder = { Text("Message") }, shape = RoundedCornerShape(28.dp), maxLines = 4)
            FilledIconButton(enabled = text.isNotBlank(), onClick = onSend) { Icon(Icons.Rounded.Send, "Send") }
        }
    }
}

@Composable
private fun EmptyChatState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(shape = RoundedCornerShape(54.dp), color = Night, tonalElevation = 8.dp, modifier = Modifier.size(240.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Rounded.Chat, null, modifier = Modifier.size(62.dp), tint = Lilac)
                    Text("Choose a scene chat", fontWeight = FontWeight.Black, color = Color.White, modifier = Modifier.padding(top = 10.dp))
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
        MessageUi("h1", "Никольский, я скинула тебе карту. Красная точка — конечная.", false, "15:35"),
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
