package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantDao: PlantDao,
) : PlantRepository {

    override fun getAllPlants(): Flow<List<PlantModel>> = plantDao.selectAll()
        .map { entities -> entities.map { PlantModel(it) } }

    override suspend fun addPlant(plant: PlantModel) = plantDao.insertReplace(PlantEntity(plant))
    override suspend fun removePlant(plantId: Int) = plantDao.deleteById(plantId)

}