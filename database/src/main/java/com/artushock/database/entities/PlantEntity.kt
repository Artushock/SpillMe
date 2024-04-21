package com.artushock.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val plantDate: Long,
    val plantTypeId: Int?,
    val plantLocationId: Int?,
)