package com.programperfect.messagesexpressive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.programperfect.messagesexpressive.core.designsystem.MessagesExpressiveTheme
import com.programperfect.messagesexpressive.ui.MessagesApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessagesExpressiveTheme {
                MessagesApp()
            }
        }
    }
}
