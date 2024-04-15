package com.artushock.apps.spillme.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.artushock.apps.spillme.db.entities.PlantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao : BaseDao<PlantEntity> {
    @Query("select *  from `plants`")
    fun selectAll(): Flow<List<PlantEntity>>

    @Query("delete from `plants` where id=:plantId")
    suspend fun deleteById(plantId: Int)
}