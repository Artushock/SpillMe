package com.artushock.apps.spillme.ui.addplant.type

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artushock.apps.spillme.ui.addplant.type.model.AddTypeScreenState
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField

@Composable
fun AddBasicDescription(
    state: AddTypeScreenState,
    onStateModified: (AddTypeScreenState) -> Unit,
    onNext: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            EditTextField(
                labelText = "Name",
                value = state.name,
                onValueChanged = { onStateModified(state.copy(name = it)) }
            )

            EditTextField(
                labelText = "Description",
                value = state.description,
                onValueChanged = { onStateModified(state.copy(description = it)) }
            )
        }

        Button(
            onClick = {
                if (state.name.isBlank()) {
                    Toast.makeText(context, "Fill plant type name", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (state.description.isBlank()) {
                    Toast.makeText(context, "Fill plant type description", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }
                onNext()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = getButtonColors()
        ) {
            Text(text = "NEXT", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}