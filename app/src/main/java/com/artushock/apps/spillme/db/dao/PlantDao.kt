package com.artushock.apps.spillme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artushock.apps.spillme.db.entities.PlantEntity

@Dao
interface PlantDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplacePlant(plantEntity: PlantEntity)

//    @Query("select *  from plants")
//    suspend fun selectAllPlants(): List<PlantEntity>

}