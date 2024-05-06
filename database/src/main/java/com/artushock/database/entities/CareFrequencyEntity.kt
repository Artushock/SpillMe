package com.artushock.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "care_frequency")
data class CareFrequencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val wateringFrequency: Int?,
    val sprayingFrequency: Int?,
    val rubbingFrequency: Int?,
    val transplantingFrequency: Int?,
    val bathingFrequency: Int?,
)