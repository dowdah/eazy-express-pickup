package com.dowdah.easypickup.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 定义亮色主题的颜色方案
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    // ... 其他颜色可以从 Color.kt 中继续添加
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    onBackground = md_theme_light_onBackground,
    onSurface = md_theme_light_onSurface,
)

// 定义暗色主题的颜色方案
private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    // ... 其他颜色可以从 Color.kt 中继续添加
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    onBackground = md_theme_dark_onBackground,
    onSurface = md_theme_dark_onSurface,
)

/**
 * 这是应用的主题入口 Composable 函数。
 * 整个应用的UI都应该被包裹在这个函数里，以确保所有组件都能应用统一的主题。
 *
 * @param darkTheme 是否使用暗色主题，默认为跟随系统设置。
 * @param dynamicColor 是否启用动态配色（Monet），只在 Android 12+ 上生效。
 * @param content 具体的UI内容。
 */
@Composable
fun EasyPickupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // 假设 minSdk >= 31 (Android 12)，动态颜色始终可用
    content: @Composable () -> Unit
) {
    // 决定使用哪个颜色方案
    val colorScheme = when {
        // 由于 minSdk >= 33，不再需要版本检查
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // 設置系統狀態欄顏色，使其與App主題協調
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // ❌ 下面这行代码已被移除，因为它已被废弃
            // window.statusBarColor = Color.Transparent.toArgb()

            // ✅ 保留这行，它负责控制状态栏图标的颜色，现在依然需要
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // 应用 MaterialTheme，将颜色方案、排版等应用到 content 中
    MaterialTheme(
        colorScheme = colorScheme,
        // typography = Typography, // 你可以在此定义字体样式
        content = content
    )
}
