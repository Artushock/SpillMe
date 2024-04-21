package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.repositories.mapers.toDBEntity
import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import com.artushock.apps.spillme.repositories.models.plants.CareFrequency
import com.artushock.apps.spillme.repositories.models.plants.Fertilizer
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantDao: com.artushock.database.dao.PlantDao,
    private val careFrequencyDao: com.artushock.database.dao.CareFrequencyDao,
    private val fertilizerDao: com.artushock.database.dao.FertilizerDao,
    private val fertilizerCareJoinDao: com.artushock.database.dao.FertilizerCareJoinDao,
    private val plantTypeDao: com.artushock.database.dao.PlantTypeDao,
) : PlantRepository {

    override fun getAllPlants(): Flow<List<PlantModel>> = plantDao.selectAll()
        .map { entities -> entities.map { PlantModel(it) } }

    override suspend fun addPlant(plant: PlantModel) = plantDao.insertReplace(plant.toDBEntity())

    override suspend fun removePlant(plantId: Int) = plantDao.deleteById(plantId)

    override suspend fun saveFertilizer(fertilizer: Fertilizer) =
        fertilizerDao.insertAbortId(fertilizer.toDBEntity()).toInt()

    override suspend fun saveCare(care: CareFrequency) =
        careFrequencyDao.insertAbortId(care.toDBEntity()).toInt()

    override suspend fun connectFertilizerWithCare(fertilizerId: Int, careId: Int) {
        fertilizerCareJoinDao.insertAbort(
            com.artushock.database.entities.FertilizerCareJoinEntity(
                fertilizerId = fertilizerId,
                careId = careId
            )
        )
    }

    override suspend fun savePlantType(plantType: PlantType) =
        plantTypeDao.insertAbort(plantType.toDBEntity())

}