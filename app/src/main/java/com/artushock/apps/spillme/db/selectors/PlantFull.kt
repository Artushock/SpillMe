package com.artushock.apps.spillme.db.selectors

import androidx.room.Embedded
import androidx.room.Relation
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.db.entities.PlantTypeEntity

data class PlantFull(
    @Embedded
    val plant: PlantEntity,
    @Relation(
        entity = PlantTypeEntity::class,
        parentColumn = "plantTypeId",
        entityColumn = "id"
    )
    val plantType: PlantTypeFull?
)
