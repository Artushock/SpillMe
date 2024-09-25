package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.PlantTypeConditions

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
) {
    constructor(conditions: PlantTypeConditions) : this(
        id = 0,
        minTemperature = conditions.minTemp,
        maxTemperature = conditions.maxTemp,
        minHumidity = conditions.minHumidity,
        maxHumidity = conditions.maxHumidity,
        lightningType = conditions.lighting.id,
        soilId = -1, // Add in the next version
    )
}
