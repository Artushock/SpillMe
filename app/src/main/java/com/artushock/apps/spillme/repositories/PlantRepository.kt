package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.entities.PlantEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
) {

    fun getAllPlants(): Flow<List<PlantEntity>> = plantDao.selectAll()
    suspend fun addPlant(plantEntity: PlantEntity) = plantDao.insertReplace(plantEntity)
}