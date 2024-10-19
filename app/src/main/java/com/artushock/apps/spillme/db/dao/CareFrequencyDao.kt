package com.artushock.apps.spillme.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.artushock.apps.spillme.db.entities.CareFrequencyEntity

@Dao
interface CareFrequencyDao : BaseDao<CareFrequencyEntity> {
    @Query("""
        SELECT cf.*
        FROM plants p
        LEFT JOIN plant_type pt ON p.plantTypeId = pt.id
        LEFT JOIN care_frequency cf ON pt.careId = cf.id
        WHERE p.id = :plantId
        LIMIT 1
    """)
    suspend fun selectByPlantId(plantId: Int): CareFrequencyEntity
}