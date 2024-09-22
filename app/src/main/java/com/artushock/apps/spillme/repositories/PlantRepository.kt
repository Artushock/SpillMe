package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.db.dao.FertilizerDao
import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.entities.FertilizerEntity
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.repositories.models.PlantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
    private val fertilizerDao: FertilizerDao,
) {

    fun getAllPlants(): Flow<List<PlantModel>> = plantDao.selectAll()
        .map { entities -> entities.map { PlantModel(it) } }

    suspend fun addPlant(plantEntity: PlantEntity) = plantDao.insertReplace(plantEntity)

    suspend fun addFertilizer(fertilizerEntity: FertilizerEntity) = fertilizerDao.insertReplace(fertilizerEntity)
}