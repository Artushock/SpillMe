package com.artushock.apps.spillme.ui.base.edittext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artushock.apps.spillme.ui.base.colors.getTextFieldColors

@Composable
fun EditTextField(labelText: String, value: String, isError: Boolean, onValueChanged: ((String) -> Unit)?) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged?.let { lambda -> lambda(it) } },
        label = { Text(text = labelText) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = getTextFieldColors(),
        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        isError= isError,
    )
}

