package com.dowdah.easypickup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dowdah.easypickup.ui.theme.EasyPickupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ 启用 Edge-to-Edge 模式
        // 这行代码是实现沉浸式界面的关键，它让你的应用内容可以延伸到系统栏（状态栏/导航栏）的后面。
        // 这是目前官方最推荐的方式，它自动处理了窗口标志和系统栏颜色。
        enableEdgeToEdge()

        setContent {
            // EasyPickupTheme 是你在 ui/theme/ 中定义的应用主题，它会应用你设定的颜色和样式。
            EasyPickupTheme {
                // Scaffold 是一个 Material 3 布局组件，它提供了标准的屏幕结构，
                // 比如顶部栏、底部栏等，并且能正确处理因系统栏产生的边距（insets）。
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 这里是你应用的主要内容。
                    // `innerPadding` 是 Scaffold 计算出的安全边距，你应该将它应用到你的内容上，
                    // 以防止内容被状态栏或导航栏遮挡。
                    AppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * 这是一个示例的应用内容 Composable。
 * 在实际开发中，这里会被你的导航图（NavHost）或者主屏幕所取代。
 */
@Composable
fun AppContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "应用主界面")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EasyPickupTheme {
        AppContent(modifier = Modifier.fillMaxSize())
    }
}
