package com.artushock.apps.spillme.ui.models

import org.joda.time.DateTime

data class PlantModel(
    val id: Int,
    val name: String,
    val description: String,
    val plantDate: DateTime,
    val plantType: PlantType?,
    val location: PlantLocation?,
    val photo: String = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
)

