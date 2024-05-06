package com.artushock.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_type")
data class PlantTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val careId: Int?,
    val conditionsId: Int?,
)
