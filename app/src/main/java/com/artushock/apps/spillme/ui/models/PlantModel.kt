package com.artushock.apps.spillme.ui.models

import org.joda.time.DateTime

data class PlantModel (
    val id: Int,
    val name: String,
    val description: String,
    val plantDate: DateTime,
    val plantType: PlantType,
    val location: PlantLocation,
)


