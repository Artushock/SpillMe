package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Fertilizer
import com.artushock.apps.spillme.ui.base.IconPlus
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.base.sliders.IntervalSlider

@Composable
fun AddNewFertilizerDialog(
    onDismiss: () -> Unit,
    onAddItem: (Fertilizer) -> Unit,
) {
    var txtFertilizerName by rememberSaveable { mutableStateOf("") }
    var frequency by rememberSaveable { mutableIntStateOf(1) }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors =
            CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Adding new fertilizer",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                EditTextField(labelText = "Fertilizer name",
                    value = txtFertilizerName,
                    onValueChanged = { txtFertilizerName = it })
                IntervalSlider(
                    isCheckboxVisible = false,
                    name = "Fertilizer",
                    units = "months",
                    minValue = 1,
                    maxValue = 24,
                    defaultValue = 1,
                    valueChangeListener = { frequency = it ?: 1 })
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { onAddItem(Fertilizer(txtFertilizerName, frequency)) }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconPlus()
                            Text(text = "ADD", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}