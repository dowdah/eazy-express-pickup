package com.dowdah.easypickup.ui.profile

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.dowdah.easypickup.ui.theme.EasyPickupTheme

// 个人中心界面的状态
data class ProfileUiState(
    val userName: String,
    val phoneNumber: String,
    val barcodeBitmap: Bitmap?, // 条形码的Bitmap对象
    val isLoading: Boolean = false
)

/**
 * 个人中心界面的主Composable函数。
 *
 * @param uiState 界面的状态，包含用户信息和条形码。
 * @param onLogoutClicked 登出按钮点击事件。
 * @param onManageAccountsClicked 管理账户按钮点击事件。
 */
@OptIn(ExperimentalMaterial3Api::class) // 明确表示我们接受使用实验性的Material3 API
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onLogoutClicked: () -> Unit,
    onManageAccountsClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            // 顶部应用栏
            TopAppBar(
                title = { Text("个人中心") },
                actions = {
                    IconButton(onClick = onManageAccountsClicked) {
                        Icon(Icons.Default.Settings, contentDescription = "管理账户")
                    }
                    IconButton(onClick = onLogoutClicked) {
                        // 使用了推荐的 AutoMirrored 版本，以更好地支持RTL布局
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "登出")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 用户信息区域
            UserInfoSection(userName = uiState.userName, phoneNumber = uiState.phoneNumber)

            Spacer(modifier = Modifier.height(32.dp))

            // 条形码显示区域
            BarcodeSection(barcodeBitmap = uiState.barcodeBitmap, isLoading = uiState.isLoading)
        }
    }
}

@Composable
private fun UserInfoSection(userName: String, phoneNumber: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 用户头像占位符
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.firstOrNull()?.toString() ?: "U",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun BarcodeSection(barcodeBitmap: Bitmap?, isLoading: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp), // 给卡片一个最小高度
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "身份条形码",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 根据状态显示不同内容
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(vertical = 40.dp))
                }
                barcodeBitmap != null -> {
                    // 显示条形码图片
                    Image(
                        bitmap = barcodeBitmap.asImageBitmap(),
                        contentDescription = "身份条形码",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp), // 固定条形码高度
                        contentScale = ContentScale.FillWidth // 拉伸以填充宽度
                    )
                }
                else -> {
                    // 加载失败或无数据时显示
                    Text(
                        "无法加载条形码",
                        modifier = Modifier.padding(vertical = 40.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "请向工作人员出示此码",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// --- 预览 ---

@Preview(showBackground = true, name = "个人中心预览")
@Composable
fun ProfileScreenPreview() {
    EasyPickupTheme {
        // 创建一个假的条形码Bitmap用于预览
        val barcodeBitmap = createDummyBarcodeBitmap()
        val uiState = ProfileUiState(
            userName = "张三",
            phoneNumber = "188****5678",
            barcodeBitmap = barcodeBitmap
        )
        ProfileScreen(
            uiState = uiState,
            onLogoutClicked = {},
            onManageAccountsClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "个人中心加载中预览")
@Composable
fun ProfileScreenLoadingPreview() {
    EasyPickupTheme {
        val uiState = ProfileUiState(
            userName = "李四",
            phoneNumber = "139****1234",
            barcodeBitmap = null,
            isLoading = true // 设置为加载中状态
        )
        ProfileScreen(
            uiState = uiState,
            onLogoutClicked = {},
            onManageAccountsClicked = {}
        )
    }
}

/**
 * 辅助函数：创建一个用于预览的假条形码Bitmap。
 * 实际应用中，这个Bitmap会由ML Kit或ZXing库生成。
 */
@Composable
private fun createDummyBarcodeBitmap(): Bitmap {
    val width = with(LocalDensity.current) { 300.dp.toPx().toInt() }
    val height = with(LocalDensity.current) { 120.dp.toPx().toInt() }
    // 使用 KTX 扩展函数，代码更简洁
    val bitmap = createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(Color.WHITE) // 背景色
    val paint = android.graphics.Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
    }
    // 随机画一些黑线来模拟条形码
    for (i in 0 until width step 10) {
        if (Math.random() > 0.3) {
            canvas.drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), paint)
        }
    }
    return bitmap
}

