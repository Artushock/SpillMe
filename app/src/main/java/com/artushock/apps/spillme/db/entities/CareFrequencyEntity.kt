package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.CareFrequency

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
    constructor(domain: CareFrequency) : this(
        id = domain.id,
        wateringFrequency = domain.wateringFrequency,
        sprayingFrequency = domain.sprayingFrequency,
        rubbingFrequency = domain.rubbingFrequency,
        transplantingFrequency = domain.transplantingFrequency,
        bathingFrequency = domain.bathingFrequency,
    )
}
