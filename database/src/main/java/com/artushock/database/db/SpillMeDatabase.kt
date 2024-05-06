package com.artushock.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artushock.database.dao.CareFrequencyDao
import com.artushock.database.dao.ConditionsDao
import com.artushock.database.dao.FertilizerCareJoinDao
import com.artushock.database.dao.FertilizerDao
import com.artushock.database.dao.PlantDao
import com.artushock.database.dao.PlantTypeDao
import com.artushock.database.entities.CareFrequencyEntity
import com.artushock.database.entities.ConditionsEntity
import com.artushock.database.entities.FertilizerCareJoinEntity
import com.artushock.database.entities.FertilizerEntity
import com.artushock.database.entities.PlantEntity
import com.artushock.database.entities.PlantTypeEntity

@Database(
    entities = [
        PlantEntity::class,
        PlantTypeEntity::class,
        FertilizerEntity::class,
        FertilizerCareJoinEntity::class,
        ConditionsEntity::class,
        CareFrequencyEntity::class,
    ],
    version = 1
)
abstract class SpillMeDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun plantTypeDao(): PlantTypeDao

    abstract fun fertilizerDao(): FertilizerDao

    abstract fun fertilizerCareJoinDao(): FertilizerCareJoinDao

    abstract fun conditionsDao(): ConditionsDao

    abstract fun careFrequencyDao(): CareFrequencyDao
}
