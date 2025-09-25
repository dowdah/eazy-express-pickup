package com.dowdah.easypickup.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dowdah.easypickup.ui.components.AppTextField
import com.dowdah.easypickup.ui.components.PrimaryButton
import com.dowdah.easypickup.ui.theme.EasyPickupTheme

// 定义登录界面的状态，用于驱动UI更新
// isLoading: 是否正在加载（例如，正在请求验证码或登录）
data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * 登录界面的主Composable函数。
 * 它负责组织整个登录页面的布局和组件。
 *
 * @param onLoginClicked 当用户点击登录/获取验证码按钮时的回调，通常会触发ViewModel中的逻辑。
 */
@Composable
fun LoginScreen(
    // uiState: LoginUiState, // 从ViewModel传入
    onLoginClicked: (phone: String, code: String) -> Unit
) {
    // 使用 remember 来持有UI状态，当状态改变时，Compose会自动重绘UI
    var phoneNumber by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    var isCodeSent by remember { mutableStateOf(false) } // 标记验证码是否已发送
    val uiState = LoginUiState() // 临时模拟的状态

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 应用标题
            Text(
                text = "欢迎登录",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "请输入您的手机号以继续",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(48.dp))

            // 手机号输入框
            AppTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                labelText = "手机号",
                placeholderText = "请输入11位手机号",
                keyboardType = KeyboardType.Phone
            )

            // 如果验证码已发送，则显示验证码输入框
            if (isCodeSent) {
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    value = verificationCode,
                    onValueChange = { verificationCode = it },
                    labelText = "验证码",
                    placeholderText = "请输入6位验证码",
                    keyboardType = KeyboardType.NumberPassword
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 如果正在加载，显示一个加载指示器
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                // 主要操作按钮：根据是否已发送验证码，显示不同文本
                PrimaryButton(
                    text = if (isCodeSent) "登录" else "获取验证码",
                    onClick = {
                        if (isCodeSent) {
                            // 调用登录逻辑
                            onLoginClicked(phoneNumber, verificationCode)
                        } else {
                            // 模拟发送验证码成功
                            isCodeSent = true
                            // 实际开发中，这里应调用 ViewModel 发送验证码
                        }
                    },
                    // 简单的校验逻辑：手机号长度正确才能点击
                    enabled = phoneNumber.length == 11
                )
            }

            // 如果已发送验证码，提供一个切换账号的选项
            if (isCodeSent) {
                TextButton(onClick = {
                    isCodeSent = false
                    verificationCode = ""
                }) {
                    Text("使用其他手机号登录")
                }
            }
        }
    }
}

// @Preview 注解让我们可以直接在Android Studio的设计视图中预览UI，非常方便
@Preview(showBackground = true, name = "输入手机号预览")
@Composable
fun LoginScreenPreview() {
    EasyPickupTheme {
        LoginScreen(onLoginClicked = { _, _ -> })
    }
}

@Preview(showBackground = true, name = "输入验证码预览")
@Composable
fun LoginScreenCodeSentPreview() {
    // 这是一个特殊的预览，为了展示输入验证码的状态
    EasyPickupTheme {
        // 通过直接调用并设置状态来预览特定场景
        val screenContent: @Composable () -> Unit = {
            var phoneNumber by remember { mutableStateOf("18812345678") }
            var verificationCode by remember { mutableStateOf("") }
            var isCodeSent by remember { mutableStateOf(true) } // 直接设置为true
            // ... (复制LoginScreen内部代码以便预览)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("欢迎登录", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(48.dp))
                AppTextField(
                    value = phoneNumber, onValueChange = { phoneNumber = it },
                    labelText = "手机号", placeholderText = "请输入11位手机号",
                )
                if (isCodeSent) {
                    AppTextField(
                        value = verificationCode, onValueChange = { verificationCode = it },
                        labelText = "验证码", placeholderText = "请输入6位验证码",
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryButton(text = "登录", onClick = {})
                TextButton(onClick = { /*TODO*/ }) {
                    Text("使用其他手机号登录")
                }
            }
        }
        screenContent()
    }
}
