package com.dowdah.easypickup.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * 一个通用的、填满宽度的主要操作按钮。
 * @param text 按钮上显示的文字。
 * @param onClick 按钮的点击事件回调。
 * @param modifier Modifier 用于自定义样式。
 * @param enabled 按钮是否可点击。
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}

/**
 * 一个通用的文本输入框，带有边框样式。
 * @param value 输入框当前的值。
 * @param onValueChange 值变化时的回调函数。
 * @param labelText 输入框顶部的提示标签文字。
 * @param placeholderText 输入框为空时的占位文字。
 * @param keyboardType 指定键盘类型，例如数字键盘、文本键盘等。
 * @param visualTransformation 用于格式化输入内容，例如密码隐藏。
 * @param modifier Modifier 用于自定义样式。
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    placeholderText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        placeholder = { Text(placeholderText) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
