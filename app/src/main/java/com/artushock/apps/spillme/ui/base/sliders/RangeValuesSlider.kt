package com.artushock.apps.spillme.ui.base.sliders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RangeValuesSlider(
    isCheckboxVisible: Boolean = true,
    name: String = "",
    units: String = "°C",
    minValue: Int = -30,
    maxValue: Int = 50,
    defaultValue: ClosedFloatingPointRange<Float> = 0f..25f,
    valueChangeListener: (ClosedFloatingPointRange<Float>?) -> Unit,
) {
    var checkBoxState by remember { mutableStateOf(true) }
    var sliderPosition by remember { mutableStateOf(defaultValue) }

    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        if (isCheckboxVisible) {
            Checkbox(checked = checkBoxState, onCheckedChange = {
                checkBoxState = it
                if (!it) valueChangeListener(null)
            })
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = name,
                    modifier = Modifier.align(Alignment.TopStart),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp
                )
                Text(
                    text = "${sliderPosition.start.toInt()} .. ${sliderPosition.endInclusive.toInt()} $units",
                    modifier = Modifier.align(Alignment.TopEnd),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp
                )
            }
            RangeSlider(
                value = sliderPosition,
                onValueChange = { range ->
                    sliderPosition = range
                },
                enabled = checkBoxState,
                valueRange = minValue.toFloat()..maxValue.toFloat(),
                steps = maxValue - minValue,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            )
        }
    }
}

@Preview
@Composable
fun RangeValuesSliderPreview() {
    RangeValuesSlider {}
}