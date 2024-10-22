package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.db.dao.CareFrequencyDao
import com.artushock.apps.spillme.db.dao.ConditionsDao
import com.artushock.apps.spillme.db.dao.FertilizerCareJoinDao
import com.artushock.apps.spillme.db.dao.FertilizerDao
import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.dao.PlantTypeDao
import com.artushock.apps.spillme.db.entities.CareFrequencyEntity
import com.artushock.apps.spillme.db.entities.ConditionsEntity
import com.artushock.apps.spillme.db.entities.FertilizerCareJoinEntity
import com.artushock.apps.spillme.db.entities.FertilizerEntity
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.db.entities.PlantTypeEntity
import com.artushock.apps.spillme.db.selectors.PlantFull
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
    private val fertilizerDao: FertilizerDao,
    private val careFrequencyDao: CareFrequencyDao,
    private val fertilizerCareJoinDao: FertilizerCareJoinDao,
    private val conditionsDao: ConditionsDao,
    private val plantTypeDao: PlantTypeDao,
) {

    fun getAllPlants(): Flow<List<PlantFull>> = plantDao.selectAll()

    suspend fun addPlant(plantEntity: PlantEntity): Long {
        return plantDao.insertReplaceId(plantEntity)
    }

    suspend fun addFertilizer(fertilizerEntity: FertilizerEntity, careId: Int) {
        val fertilizerId = fertilizerDao.insertReplaceId(fertilizerEntity)
        fertilizerCareJoinDao.insertReplaceId(
            FertilizerCareJoinEntity(
                fertilizerId = fertilizerId.toInt(),
                careId = careId
            )
        )
    }

    suspend fun addCare(careEntity: CareFrequencyEntity) =
        careFrequencyDao.insertReplaceId(careEntity)

    suspend fun addConditions(conditionsEntity: ConditionsEntity) =
        conditionsDao.insertReplaceId(conditionsEntity)

    suspend fun addPlantType(plantTypeEntity: PlantTypeEntity) =
        plantTypeDao.insertReplaceId(plantTypeEntity)

    suspend fun getPlantTypes(): List<PlantType> = plantTypeDao.selectAll().map { entity ->
        PlantType(entity)
    }

    suspend fun getCareByPlantId(plantId: Int) =
        careFrequencyDao.selectByPlantId(plantId).toDomain()

}