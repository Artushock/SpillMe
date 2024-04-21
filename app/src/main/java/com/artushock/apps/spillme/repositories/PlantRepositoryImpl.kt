package com.artushock.apps.spillme.repositories

import com.artushock.apps.spillme.db.dao.CareFrequencyDao
import com.artushock.apps.spillme.db.dao.FertilizerCareJoinDao
import com.artushock.apps.spillme.db.dao.FertilizerDao
import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.dao.PlantTypeDao
import com.artushock.apps.spillme.db.entities.CareFrequencyEntity
import com.artushock.apps.spillme.db.entities.FertilizerCareJoinEntity
import com.artushock.apps.spillme.db.entities.FertilizerEntity
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.db.entities.PlantTypeEntity
import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.CareFrequency
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Fertilizer
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.PlantType
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

    override suspend fun addPlant(plant: PlantModel) = plantDao.insertReplace(PlantEntity(plant))

    override suspend fun removePlant(plantId: Int) = plantDao.deleteById(plantId)

    override suspend fun saveFertilizer(fertilizer: Fertilizer) = fertilizerDao.insertAbortId(
        FertilizerEntity(fertilizer)
    ).toInt()

    override suspend fun saveCare(care: CareFrequency) = careFrequencyDao.insertAbortId(
        CareFrequencyEntity(care)
    ).toInt()

    override suspend fun connectFertilizerWithCare(fertilizerId: Int, careId: Int) {
        fertilizerCareJoinDao.insertAbort(
            FertilizerCareJoinEntity(
                fertilizerId = fertilizerId,
                careId = careId
            )
        )
    }

    override suspend fun savePlantType(plantType: PlantType) = plantTypeDao.insertAbort(
        PlantTypeEntity(plantType)
    )

}