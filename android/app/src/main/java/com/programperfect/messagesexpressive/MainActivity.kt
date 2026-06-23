package com.programperfect.messagesexpressive

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material3.Badge
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessengerTheme {
                MessengerApp()
            }
        }
    }
}

private val LightExpressiveScheme = lightColorScheme(
    primary = Color(0xFF6B35D7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF22005D),
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFFC75D4D),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDAD4),
    onTertiaryContainer = Color(0xFF410002),
    background = Color(0xFFFFF7FF),
    onBackground = Color(0xFF1D1B20),
    surface = Color(0xFFFFF7FF),
    onSurface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF7A757F),
    outlineVariant = Color(0xFFCAC4D0)
)

private val DarkExpressiveScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFFFB4A8),
    onTertiary = Color(0xFF690005),
    tertiaryContainer = Color(0xFF93000A),
    onTertiaryContainer = Color(0xFFFFDAD4),
    background = Color(0xFF141218),
    onBackground = Color(0xFFE6E0E9),
    surface = Color(0xFF141218),
    onSurface = Color(0xFFE6E0E9),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF948F99),
    outlineVariant = Color(0xFF49454F)
)

private val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(24.dp),
    large = RoundedCornerShape(32.dp),
    extraLarge = RoundedCornerShape(40.dp)
)

@Composable
fun MessengerTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val dynamic = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val dark = false
    val scheme = when {
        dynamic && dark -> dynamicDarkColorScheme(context)
        dynamic -> dynamicLightColorScheme(context)
        dark -> DarkExpressiveScheme
        else -> LightExpressiveScheme
    }

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography(),
        shapes = ExpressiveShapes,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessengerApp() {
    val configuration = LocalConfiguration.current
    val isWide = configuration.screenWidthDp >= 720
    var selectedConversationId by rememberSaveable { mutableStateOf<String?>(null) }
    val currentConversationId = selectedConversationId ?: if (isWide) fakeConversations.first().id else null
    val currentConversation = fakeConversations.firstOrNull { it.id == currentConversationId }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.36f),
                            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.30f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            if (isWide) {
                WideMessengerShell(
                    selectedConversationId = currentConversationId,
                    onConversationSelected = { selectedConversationId = it },
                    selectedConversation = currentConversation
                )
            } else {
                PhoneMessengerShell(
                    selectedConversation = currentConversation,
                    onConversationSelected = { selectedConversationId = it },
                    onBack = { selectedConversationId = null }
                )
            }
        }
    }
}

@Composable
private fun WideMessengerShell(
    selectedConversationId: String?,
    selectedConversation: ConversationUi?,
    onConversationSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NavigationRail(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(32.dp)),
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.78f)
        ) {
            Spacer(Modifier.height(12.dp))
            NavigationRailItem(
                selected = true,
                onClick = {},
                icon = { Icon(Icons.Rounded.Chat, contentDescription = null) },
                label = { Text("Chats") }
            )
            NavigationRailItem(
                selected = false,
                onClick = {},
                icon = { Icon(Icons.Rounded.Archive, contentDescription = null) },
                label = { Text("Archive") }
            )
            NavigationRailItem(
                selected = false,
                onClick = {},
                icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                label = { Text("Settings") }
            )
        }

        Surface(
            modifier = Modifier
                .width(420.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(36.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
            tonalElevation = 3.dp
        ) {
            ConversationListScreen(
                selectedConversationId = selectedConversationId,
                onConversationSelected = onConversationSelected,
                showTopBar = true
            )
        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(36.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
            tonalElevation = 4.dp
        ) {
            if (selectedConversation != null) {
                ChatScreen(
                    conversation = selectedConversation,
                    onBack = null
                )
            } else {
                EmptyChatState()
            }
        }
    }
}

@Composable
private fun PhoneMessengerShell(
    selectedConversation: ConversationUi?,
    onConversationSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    if (selectedConversation == null) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Icon(Icons.Rounded.Edit, contentDescription = "New chat")
                }
            },
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)) {
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = { Icon(Icons.Rounded.Inbox, contentDescription = null) },
                        label = { Text("Chats") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Rounded.Archive, contentDescription = null) },
                        label = { Text("Archive") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                        label = { Text("Setup") }
                    )
                }
            },
            contentWindowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp)
        ) { padding ->
            ConversationListScreen(
                modifier = Modifier.padding(padding),
                selectedConversationId = null,
                onConversationSelected = onConversationSelected,
                showTopBar = true
            )
        }
    } else {
        ChatScreen(conversation = selectedConversation, onBack = onBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConversationListScreen(
    modifier: Modifier = Modifier,
    selectedConversationId: String?,
    onConversationSelected: (String) -> Unit,
    showTopBar: Boolean
) {
    var query by rememberSaveable { mutableStateOf("") }
    var activeTab by rememberSaveable { mutableStateOf("all") }
    val filtered = remember(query, activeTab) {
        fakeConversations.filter { conversation ->
            val tabMatches = when (activeTab) {
                "pinned" -> conversation.pinned
                "unread" -> conversation.unreadCount > 0
                else -> true
            }
            val queryMatches = query.isBlank() ||
                conversation.name.contains(query, ignoreCase = true) ||
                conversation.lastMessage.contains(query, ignoreCase = true)
            tabMatches && queryMatches
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        if (showTopBar) {
            LargeTopAppBar(
                title = {
                    Text(
                        "Messages",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Rounded.MoreVert, contentDescription = null) }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
            placeholder = { Text("Search conversations") },
            singleLine = true,
            shape = RoundedCornerShape(28.dp)
        )

        Row(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = activeTab == "all",
                onClick = { activeTab = "all" },
                label = { Text("All") }
            )
            FilterChip(
                selected = activeTab == "pinned",
                onClick = { activeTab = "pinned" },
                label = { Text("Pinned") },
                leadingIcon = { Icon(Icons.Rounded.Star, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            FilterChip(
                selected = activeTab == "unread",
                onClick = { activeTab = "unread" },
                label = { Text("Unread") }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(filtered, key = { it.id }) { conversation ->
                ConversationRow(
                    conversation = conversation,
                    selected = selectedConversationId == conversation.id,
                    onClick = { onConversationSelected(conversation.id) }
                )
            }
        }
    }
}

@Composable
private fun ConversationRow(
    conversation: ConversationUi,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(28.dp),
        color = if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
        tonalElevation = if (selected) 2.dp else 0.dp
    ) {
        ListItem(
            headlineContent = {
                Text(
                    conversation.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = if (conversation.unreadCount > 0) FontWeight.ExtraBold else FontWeight.SemiBold
                )
            },
            supportingContent = {
                Text(
                    conversation.lastMessage,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingContent = {
                Avatar(conversation)
            },
            trailingContent = {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        conversation.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    AnimatedVisibility(conversation.unreadCount > 0) {
                        Badge(containerColor = MaterialTheme.colorScheme.tertiary) {
                            Text(conversation.unreadCount.toString())
                        }
                    }
                }
            },
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun Avatar(conversation: ConversationUi) {
    Surface(
        modifier = Modifier.size(54.dp),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 18.dp,
            bottomStart = 18.dp,
            bottomEnd = 28.dp
        ),
        color = conversation.avatarColor,
        tonalElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                conversation.initials,
                color = Color.White,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    conversation: ConversationUi,
    onBack: (() -> Unit)?
) {
    var composerText by rememberSaveable(conversation.id) { mutableStateOf("") }
    val messages = remember(conversation.id) { fakeMessages[conversation.id].orEmpty() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Avatar(conversation)
                        Spacer(Modifier.width(10.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(conversation.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(
                                "online for scene",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Rounded.Call, contentDescription = null) }
                    IconButton(onClick = {}) { Icon(Icons.Rounded.Videocam, contentDescription = null) }
                    IconButton(onClick = {}) { Icon(Icons.Rounded.MoreVert, contentDescription = null) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
                )
            )
        },
        bottomBar = {
            MessageComposer(
                text = composerText,
                onTextChange = { composerText = it },
                onSend = { composerText = "" }
            )
        },
        contentWindowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(messages, key = { it.id }) { message ->
                MessageBubble(message)
            }
        }
    }
}

@Composable
private fun MessageBubble(message: MessageUi) {
    val outgoing = message.outgoing
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (outgoing) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .animateContentSize(),
            color = if (outgoing) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.72f),
            contentColor = if (outgoing) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
            tonalElevation = if (outgoing) 3.dp else 1.dp,
            shape = RoundedCornerShape(
                topStart = 28.dp,
                topEnd = 28.dp,
                bottomStart = if (outgoing) 28.dp else 8.dp,
                bottomEnd = if (outgoing) 8.dp else 28.dp
            )
        ) {
            Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
                Text(message.text, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(3.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        message.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (outgoing) {
                        Icon(
                            if (message.read) Icons.Rounded.DoneAll else Icons.Rounded.Done,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageComposer(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Surface(
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.97f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .navigationBarsPadding()
                .padding(10.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.Add, contentDescription = "Attach")
            }
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Message") },
                shape = RoundedCornerShape(28.dp),
                maxLines = 5
            )
            FilledIconButton(
                enabled = text.isNotBlank(),
                onClick = onSend
            ) {
                Icon(Icons.Rounded.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
private fun EmptyChatState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            tonalElevation = 4.dp,
            modifier = Modifier.size(220.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Rounded.Chat,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Choose a scene chat",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
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
    val avatarColor: Color
)

data class MessageUi(
    val id: String,
    val text: String,
    val outgoing: Boolean,
    val time: String,
    val read: Boolean = true
)

private val fakeConversations = listOf(
    ConversationUi(
        id = "hwan",
        name = "Хван",
        initials = "ХВ",
        lastMessage = "Маршрут Грушина пришлю отдельной картой.",
        time = "15:42",
        unreadCount = 2,
        pinned = true,
        avatarColor = Color(0xFF6B35D7)
    ),
    ConversationUi(
        id = "nikolsky",
        name = "Никольский",
        initials = "АН",
        lastMessage = "Посмотри документ на планшете.",
        time = "14:18",
        pinned = true,
        avatarColor = Color(0xFF006A60)
    ),
    ConversationUi(
        id = "karaev",
        name = "Караев",
        initials = "СК",
        lastMessage = "Выйду через служебный вход.",
        time = "13:07",
        unreadCount = 1,
        avatarColor = Color(0xFFC75D4D)
    ),
    ConversationUi(
        id = "prop",
        name = "Реквизит",
        initials = "РК",
        lastMessage = "Готовы паспорт, ориентировка и маршрут.",
        time = "вчера",
        avatarColor = Color(0xFF7D5260)
    )
)

private val fakeMessages = mapOf(
    "hwan" to listOf(
        MessageUi("h1", "Никольский, я скинула тебе карту. Красная точка — конечная.", false, "15:35"),
        MessageUi("h2", "Получил. Это по Грушину?", true, "15:36"),
        MessageUi("h3", "Да. Документ тоже приложила. Не официальный рапорт, а служебная выписка.", false, "15:37"),
        MessageUi("h4", "Понял. В кадре читаю с планшета.", true, "15:38"),
        MessageUi("h5", "Только не называй это уголовным делом. Это проверка по линии ведомств.", false, "15:42")
    ),
    "nikolsky" to listOf(
        MessageUi("n1", "Хван прислала таблицу по офшорным счетам.", false, "14:12"),
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
