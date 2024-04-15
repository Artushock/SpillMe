package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getAllPlants(): Flow<List<PlantModel>>

    suspend fun addPlant(plant: PlantModel)
    suspend fun removePlant(plantId: Int)
}