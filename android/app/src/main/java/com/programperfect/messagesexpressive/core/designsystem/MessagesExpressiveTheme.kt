@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package com.programperfect.messagesexpressive.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Violet = Color(0xFF7B2CFF)
val ElectricViolet = Color(0xFFB85CFF)
val Lilac = Color(0xFFD9B8FF)
val SoftLilac = Color(0xFFF1E6FF)
val Coral = Color(0xFFFF7D8A)
val Cyan = Color(0xFF00A8E8)
val Lime = Color(0xFFD8FF6A)
val Ink = Color(0xFF241126)
val Night = Color(0xFF19051F)
val Frost = Color(0xFFFFF7FF)

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
fun MessagesExpressiveTheme(content: @Composable () -> Unit) {
    MaterialExpressiveTheme(
        colorScheme = ExpressiveScheme,
        motionScheme = MotionScheme.expressive(),
        shapes = ExpressiveShapes,
        typography = ExpressiveTypography,
        content = content
    )
}

enum class ExpressiveAccent {
    Violet,
    Teal,
    Coral,
    Night
}

data class AccentColors(
    val avatar: Color,
    val card: Color,
    val onCard: Color
)

fun ExpressiveAccent.colors(): AccentColors = when (this) {
    ExpressiveAccent.Violet -> AccentColors(Violet, Lilac, Ink)
    ExpressiveAccent.Teal -> AccentColors(Color(0xFF00796B), Lime, Color(0xFF152000))
    ExpressiveAccent.Coral -> AccentColors(Color(0xFFC75D4D), Color(0xFFFFB0A5), Color(0xFF350003))
    ExpressiveAccent.Night -> AccentColors(Color(0xFF7D5260), Color(0xFF2A0B33), Color(0xFFFFE8FF))
}

