package com.artushock.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "fertilizer_care_join",
    primaryKeys = ["fertilizerId", "careId" ],
    foreignKeys = [
        ForeignKey(entity = FertilizerEntity::class, parentColumns = ["id"], childColumns = ["fertilizerId"] ),
        ForeignKey(entity = CareFrequencyEntity::class, parentColumns = ["id"], childColumns = ["careId"] )
    ]
)
data class FertilizerCareJoinEntity(
    val fertilizerId: Int,
    val careId: Int,
)
