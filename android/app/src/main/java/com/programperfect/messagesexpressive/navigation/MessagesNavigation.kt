@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package com.programperfect.messagesexpressive.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationItemIconPosition
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailDefaults
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailItemDefaults
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.programperfect.messagesexpressive.core.designsystem.Coral
import com.programperfect.messagesexpressive.core.designsystem.Ink
import com.programperfect.messagesexpressive.core.designsystem.Lilac
import com.programperfect.messagesexpressive.core.designsystem.Night

@Composable
fun PixelRail() {
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
fun PixelBottomBar() {
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



