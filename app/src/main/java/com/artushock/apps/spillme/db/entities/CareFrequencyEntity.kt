package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.PlantTypeCare

@Entity(tableName = "care_frequency")
data class CareFrequencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val wateringFrequency: Int?,
    val sprayingFrequency: Int?,
    val rubbingFrequency: Int?,
    val transplantingFrequency: Int?,
    val bathingFrequency: Int?,
) {
    constructor(plantTypeCare: PlantTypeCare) : this(
        id = 0,
        wateringFrequency = plantTypeCare.wateringFrequency,
        sprayingFrequency = plantTypeCare.sprayingFrequency,
        rubbingFrequency = plantTypeCare.rubbingFrequency,
        transplantingFrequency = plantTypeCare.transplantingFrequency,
        bathingFrequency = plantTypeCare.bathingFrequency,
    )
}
