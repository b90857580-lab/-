package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = AppPrimary,
    onPrimary = AppOnPrimary,
    primaryContainer = AppGradientStart,
    onPrimaryContainer = AppTextPrimary,
    secondary = AppPrimary,
    secondaryContainer = AppSurface,
    onSecondaryContainer = AppTextPrimary,
    tertiary = AppGradientEnd,
    background = AppBackground,
    surface = AppSurface,
    surfaceVariant = AppNavBar,
    onBackground = AppTextPrimary,
    onSurface = AppTextPrimary,
    onSurfaceVariant = AppTextPrimary,
    outline = AppOutline,
    outlineVariant = AppOutline, // Setting up generic dividers correctly
    scrim = Color.Black
  )

private val LightColorScheme = DarkColorScheme // Force aesthetic theme

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  // Turn off dynamic color to enforce our beautiful custom theme
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
