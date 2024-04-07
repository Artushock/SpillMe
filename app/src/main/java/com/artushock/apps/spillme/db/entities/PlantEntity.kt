package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artushock.apps.spillme.repositories.models.PlantModel

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val plantDate: Long,
    val plantTypeId: Int?,
    val plantLocationId: Int?,
) {
    constructor(plantModel: PlantModel) : this(
        id = plantModel.id,
        name = plantModel.name,
        description = plantModel.description,
        plantDate = plantModel.plantDate.millis,
        plantTypeId = plantModel.plantType?.id,
        plantLocationId = plantModel.location?.id,
    )
}