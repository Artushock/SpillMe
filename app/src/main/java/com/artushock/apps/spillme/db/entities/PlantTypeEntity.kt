package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.PlantType

@Entity(tableName = "plant_type")
data class PlantTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val careId: Int?,
    val conditionsId: Int?,
) {
    constructor(domain: PlantType) : this(
        id = domain.id,
        name = domain.name,
        description = domain.description,
        careId = domain.careId,
        conditionsId = domain.conditionsId,
    )
}
