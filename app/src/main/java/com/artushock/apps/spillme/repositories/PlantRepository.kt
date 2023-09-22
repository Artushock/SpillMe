package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.entities.PlantEntity
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
) {
    fun addPlant(name: String, description: String) {
        plantDao.insertPlant(
            PlantEntity(
                id = 0,
                name = name,
                description = description
            )
        )
    }
}