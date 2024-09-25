package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_type")
data class PlantTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val careId: Int,
    val conditionsId: Int,
) {
    constructor(
        name: String,
        description: String,
        careId: Int,
        conditionsId: Int,
    ) : this(
        id = 0,
        name = name,
        description = description,
        careId = careId,
        conditionsId = conditionsId,
    )
}
