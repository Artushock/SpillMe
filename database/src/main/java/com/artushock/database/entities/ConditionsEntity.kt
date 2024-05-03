package com.artushock.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conditions")
data class ConditionsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val minTemperature: Int,
    val maxTemperature: Int,
    val minHumidity: Int,
    val maxHumidity: Int,
    val lightningType: Int,
    val soilId: Int,
)
