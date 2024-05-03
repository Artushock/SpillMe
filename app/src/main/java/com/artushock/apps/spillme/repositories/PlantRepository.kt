package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import com.artushock.apps.spillme.repositories.models.plants.CareFrequency
import com.artushock.apps.spillme.repositories.models.plants.Fertilizer
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getAllPlants(): Flow<List<PlantModel>>
    suspend fun addPlant(plant: PlantModel)
    suspend fun removePlant(plantId: Int)
    suspend fun saveFertilizer(fertilizer: Fertilizer): Int
    suspend fun saveCare(care: CareFrequency): Int
    suspend fun connectFertilizerWithCare(fertilizerId: Int, careId: Int)
    suspend fun savePlantType(plantType: PlantType)
    fun getPlantTypes(): Flow<List<PlantType>>


}