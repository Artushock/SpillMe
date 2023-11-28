package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.artushock.apps.spillme.ui.base.sliders.IntervalSlider

@Composable
fun FrequencyOfCareScreen(
    navController: NavController,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            IntervalSlider(
                name = "Watering",
                units = "days",
                minValue = 1,
                maxValue = 14,
                defaultValue = 3,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Spraying",
                units = "days",
                minValue = 1,
                maxValue = 30,
                defaultValue = 12,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Rubbing",
                units = "months",
                minValue = 1,
                maxValue = 12,
                defaultValue = 1,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Transplanting",
                units = "months",
                minValue = 6,
                maxValue = 36,
                defaultValue = 12,
                valueChangeListener = {/*todo*/ })
            IntervalSlider(
                name = "Bathing",
                units = "months",
                minValue = 6,
                maxValue = 36,
                defaultValue = 18,
                valueChangeListener = {/*todo*/ })
        }
    }
}

