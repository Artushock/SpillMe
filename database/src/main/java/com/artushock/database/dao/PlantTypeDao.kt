package com.artushock.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.artushock.database.entities.PlantTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantTypeDao : BaseDao<PlantTypeEntity> {

    @Query("select * from `plant_type`")
    fun selectAll(): Flow<List<PlantTypeEntity>>
}