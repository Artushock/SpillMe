package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fertilizer")
data class FertilizerEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val frequency: Int,
)