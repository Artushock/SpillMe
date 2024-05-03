package com.artushock.apps.spillme.ui.addplant.type

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.addplant.type.model.AddTypeScreenState
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.sliders.IntervalSlider

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FrequencyOfCareScreen(
    state: AddTypeScreenState,
    onStateModified: (AddTypeScreenState) -> Unit,
    onNext: () -> Unit
) {

    var addFertilizerDialogShown by rememberSaveable { mutableStateOf(false) }

    if (addFertilizerDialogShown) {
        AddNewFertilizerDialog(
            onDismiss = { addFertilizerDialogShown = false },
            onAddItem = { newFertilizer ->
                val editedFertilizers = state.fertilizers.toMutableList()
                editedFertilizers.add(newFertilizer)
                onStateModified(state.copy(fertilizers = editedFertilizers))
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
            IntervalSlider(
                name = "Watering",
                units = "days",
                minValue = 1,
                maxValue = 14,
                defaultValue = state.wateringFrequency,
                valueChangeListener = { wateringFrequency ->
                    wateringFrequency?.let {
                        onStateModified(
                            state.copy(wateringFrequency = it)
                        )
                    }
                })
            IntervalSlider(
                name = "Spraying",
                units = "days",
                minValue = 1,
                maxValue = 30,
                defaultValue = state.sprayingFrequency,
                valueChangeListener = { sprayingFrequency ->
                    sprayingFrequency?.let {
                        onStateModified(
                            state.copy(sprayingFrequency = it)
                        )
                    }
                })
            IntervalSlider(
                name = "Rubbing",
                units = "months",
                minValue = 1,
                maxValue = 12,
                defaultValue = state.rubbingFrequency,
                valueChangeListener = { rubbingFrequency ->
                    rubbingFrequency?.let {
                        onStateModified(
                            state.copy(rubbingFrequency = it)
                        )
                    }
                })
            IntervalSlider(
                name = "Transplanting",
                units = "months",
                minValue = 6,
                maxValue = 36,
                defaultValue = state.transplantingFrequency,
                valueChangeListener = { transplantingFrequency ->
                    transplantingFrequency?.let {
                        onStateModified(
                            state.copy(transplantingFrequency = it)
                        )
                    }
                })
            IntervalSlider(
                name = "Bathing",
                units = "months",
                minValue = 6,
                maxValue = 36,
                defaultValue = state.bathingFrequency,
                valueChangeListener = { bathingFrequency ->
                    bathingFrequency?.let {
                        onStateModified(
                            state.copy(bathingFrequency = it)
                        )
                    }
                })

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
                state.fertilizers.map {
                    SuggestionChip(
                        onClick = { /*TODO*/ },
                        label = { Text(text = "${it.name} ${it.frequency} month") })
                }
            }
        }

        Button(
            onClick = { onNext() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = getButtonColors()
        ) {
            Text(text = "NEXT", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}


