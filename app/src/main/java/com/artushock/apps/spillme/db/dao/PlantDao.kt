package com.artushock.apps.spillme.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.db.selectors.PlantFull
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao : BaseDao<PlantEntity> {
    @Query("select *  from `plants`")
    fun selectAll(): Flow<List<PlantFull>>
}