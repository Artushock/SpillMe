package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.repositories.mapers.toDBEntity
import com.artushock.apps.spillme.repositories.models.plants.CareFrequency
import com.artushock.apps.spillme.repositories.models.plants.Fertilizer
import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import com.artushock.database.dao.CareFrequencyDao
import com.artushock.database.dao.FertilizerCareJoinDao
import com.artushock.database.dao.FertilizerDao
import com.artushock.database.dao.PlantDao
import com.artushock.database.dao.PlantTypeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantDao: PlantDao,
    private val careFrequencyDao: CareFrequencyDao,
    private val fertilizerDao: FertilizerDao,
    private val fertilizerCareJoinDao: FertilizerCareJoinDao,
    private val plantTypeDao: PlantTypeDao,
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

    override fun getPlantTypes(): Flow<List<PlantType>> {
        return plantTypeDao.selectAll().map { list ->
            list.map { PlantType(it) }
        }
    }
}