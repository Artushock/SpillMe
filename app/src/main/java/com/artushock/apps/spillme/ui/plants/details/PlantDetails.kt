package com.artushock.apps.spillme.ui.plants.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlantDetails(
    plantId: Int?
) {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Plant id = ${plantId ?: "error"}")
    }
}