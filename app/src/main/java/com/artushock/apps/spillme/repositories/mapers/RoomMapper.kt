package com.artushock.apps.spillme.repositories.mapers

import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import com.artushock.apps.spillme.repositories.models.plants.CareFrequency
import com.artushock.apps.spillme.repositories.models.plants.Fertilizer
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import com.artushock.database.entities.CareFrequencyEntity
import com.artushock.database.entities.FertilizerEntity
import com.artushock.database.entities.PlantEntity
import com.artushock.database.entities.PlantTypeEntity

fun PlantModel.toDBEntity() = PlantEntity(
    id = this.id,
    name = this.name,
    description = this.description,
    plantDate = this.plantDate.millis,
    plantTypeId = this.plantType?.id,
    plantLocationId = this.location?.id,
)

fun PlantType.toDBEntity() = PlantTypeEntity(
    id = this.id,
    name = this.name,
    description = this.description,
    careId = this.careId,
    conditionsId = this.conditionsId,
)

fun Fertilizer.toDBEntity() = FertilizerEntity(
    id = 0,
    name = this.name,
    frequency = this.frequency
)

fun CareFrequency.toDBEntity() = CareFrequencyEntity(
    id = this.id,
    wateringFrequency = this.wateringFrequency,
    sprayingFrequency = this.sprayingFrequency,
    rubbingFrequency = this.rubbingFrequency,
    transplantingFrequency = this.transplantingFrequency,
    bathingFrequency = this.bathingFrequency,
)