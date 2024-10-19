package com.artushock.apps.spillme.ui.base.edittext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.base.colors.getTextFieldColors

@Composable
fun EditTextField(
    labelText: String,
    value: String,
    isError: Boolean,
    onValueChanged: ((String) -> Unit)?,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged?.let { lambda -> lambda(it) } },
        label = { Text(text = labelText) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = getTextFieldColors(),
        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        isError = isError,
        visualTransformation = if (isPassword) {
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = if (!isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        trailingIcon = {
            if (isPassword) {
                val icon =
                    if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    modifier = Modifier
                        .clickable { passwordVisible = !passwordVisible }
                        .padding(8.dp)
                )
            }
        }
    )
}
