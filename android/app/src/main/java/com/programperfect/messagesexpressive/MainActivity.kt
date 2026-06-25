@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.programperfect.messagesexpressive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Material3TemplateTheme {
                TemplateApp()
            }
        }
    }
}

@Composable
private fun Material3TemplateTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6750A4),
            secondary = Color(0xFF625B71),
            tertiary = Color(0xFF7D5260),
            background = Color(0xFFFFFBFE),
            surface = Color(0xFFFFFBFE),
            surfaceVariant = Color(0xFFE7E0EC)
        ),
        content = content
    )
}

@Composable
private fun TemplateApp() {
    Scaffold(
        topBar = { TemplateTopBar() },
        bottomBar = { TemplateBottomBar() },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
                text = { Text("Create") }
            )
        }
    ) { padding ->
        TemplateContent(padding)
    }
}

@Composable
private fun TemplateTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("M3 Kotlin Template") },
        actions = {
            BadgedBox(
                badge = { Badge { Text("3") } },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(Icons.Rounded.Notifications, contentDescription = "Notifications")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
private fun TemplateContent(padding: PaddingValues) {
    val items = listOf(
        TemplateItem("Native Kotlin", "Single Activity, Jetpack Compose, Material 3"),
        TemplateItem("Design System", "MaterialTheme, colorScheme, typography, shapes"),
        TemplateItem("Ready Layout", "Top app bar, cards, navigation bar, FAB")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HeroCard() }
        items(items) { item -> TemplateCard(item) }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
private fun HeroCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Material 3 App",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Clean native Android starter on Kotlin and Jetpack Compose.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun TemplateCard(item: TemplateItem) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Rounded.Chat,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(item.subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun TemplateBottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Rounded.Person, contentDescription = null) },
            label = { Text("Profile") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
            label = { Text("Settings") }
        )
    }
}

private data class TemplateItem(
    val title: String,
    val subtitle: String
)
