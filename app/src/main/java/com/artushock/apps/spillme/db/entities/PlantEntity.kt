package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class PlantEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
)