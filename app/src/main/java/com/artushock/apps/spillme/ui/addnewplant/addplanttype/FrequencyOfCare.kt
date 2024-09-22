package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Fertilizer
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantType
import com.artushock.apps.spillme.ui.base.IconPlus
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.base.sliders.IntervalSlider
import com.artushock.apps.spillme.ui.theme.MainBrown

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FrequencyOfCareScreen(
    plantType: NewPlantType,
    onAddFertilizer: (Fertilizer) -> Unit,
) {

    var addFertilizerDialogShown by remember { mutableStateOf(false) }

    if (addFertilizerDialogShown) {
        AddNewFertilizerDialog(
            onDismiss = { addFertilizerDialogShown = false },
            onAddItem = { fertilizer ->
                onAddFertilizer(fertilizer)
                addFertilizerDialogShown = false
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = plantType.name,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = MainBrown, fontSize = 24.sp)
                )
                Text(
                    text = plantType.description,
                    fontWeight = FontWeight.Thin,
                    style = TextStyle(color = MainBrown, fontSize = 14.sp)
                )
            }

            IntervalSlider(
                name = "Watering",
                units = "days",
                minValue = 1,
                maxValue = 14,
                defaultValue = plantType.wateringFrequency,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Spraying",
                units = "days",
                minValue = 1,
                maxValue = 30,
                defaultValue = plantType.sprayingFrequency,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Rubbing",
                units = "months",
                minValue = 1,
                maxValue = 12,
                defaultValue = plantType.rubbingFrequency,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Transplanting",
                units = "months",
                minValue = 6,
                maxValue = 36,
                defaultValue = plantType.transplantingFrequency,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Bathing",
                units = "months",
                minValue = 6,
                maxValue = 36,
                defaultValue = plantType.bathingFrequency,
                valueChangeListener = {/*todo*/ })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            ) {
                Row(modifier = Modifier.align(Alignment.CenterStart)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_feeding),
                        contentDescription = "Feeding icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Fertilizers",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = { addFertilizerDialogShown = true },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus_24),
                        contentDescription = "Plus icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }


            FlowRow(
                modifier = Modifier.padding(16.dp, 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                plantType.fertilizers.map {
                    SuggestionChip(
                        onClick = { /*TODO*/ },
                        label = { Text(text = "${it.name} ${it.frequency} month") })
                }
            }
        }
    }
}

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
                    isError = false,
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

