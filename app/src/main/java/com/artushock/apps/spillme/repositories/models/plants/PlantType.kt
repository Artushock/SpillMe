package com.artushock.apps.spillme.repositories.models.plants

import com.artushock.apps.spillme.db.entities.PlantTypeEntity

data class PlantType(
    val id: Int,
    val name: String, 
    val type: Int,
    val description: String,
) {
    constructor(plantTypeEntity: PlantTypeEntity): this (
        id = plantTypeEntity.id,
                name = plantTypeEntity.name,
                type = 0,
                description = plantTypeEntity.description,
    )
    override fun toString() = name
}
