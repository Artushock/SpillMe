package com.artushock.apps.spillme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.artushock.apps.spillme.db.entities.PlantEntity

@Dao
interface PlantDao {

    @Insert
    fun insertPlant(plantEntity: PlantEntity)

    @Query("select *  from plants")
    fun selectAllPlants(): List<PlantEntity>

}