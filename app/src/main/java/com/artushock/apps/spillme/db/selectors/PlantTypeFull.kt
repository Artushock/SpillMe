package com.artushock.apps.spillme.db.selectors

import androidx.room.Embedded
import androidx.room.Relation
import com.artushock.apps.spillme.db.entities.CareFrequencyEntity
import com.artushock.apps.spillme.db.entities.PlantTypeEntity

data class PlantTypeFull(
    @Embedded val plantTypeEntity: PlantTypeEntity,
    @Relation(parentColumn = "careId", entityColumn = "id")
    val careFrequencyEntity: CareFrequencyEntity
)
