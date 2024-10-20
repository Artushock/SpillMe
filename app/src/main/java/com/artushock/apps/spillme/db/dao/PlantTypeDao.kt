package com.artushock.apps.spillme.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.artushock.apps.spillme.db.entities.PlantTypeEntity

@Dao
interface PlantTypeDao : BaseDao<PlantTypeEntity> {
    @Query("select * from `plant_type`")
    suspend fun selectAll(): List<PlantTypeEntity>
}