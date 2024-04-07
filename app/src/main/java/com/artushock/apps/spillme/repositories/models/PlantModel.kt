package com.artushock.apps.spillme.repositories.models

import com.artushock.apps.spillme.db.entities.PlantEntity
import org.joda.time.DateTime

data class PlantModel(
    val id: Int,
    val name: String,
    val description: String,
    val plantDate: DateTime,
    val plantType: PlantType?,
    val location: PlantLocation?,
    val photo: String = "",
) {
    constructor(plantEntity: PlantEntity) : this(
        id = plantEntity.id,
        name = plantEntity.name,
        description = plantEntity.description,
        plantDate = DateTime(plantEntity.plantDate),
        plantType = null,
        location = null,
        photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg", //todo
    )
}

