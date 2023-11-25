package com.artushock.apps.spillme.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artushock.apps.spillme.db.dao.CareFrequencyDao
import com.artushock.apps.spillme.db.dao.ConditionsDao
import com.artushock.apps.spillme.db.dao.FertilizerCareJoinDao
import com.artushock.apps.spillme.db.dao.FertilizerDao
import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.dao.PlantTypeDao
import com.artushock.apps.spillme.db.entities.CareFrequencyEntity
import com.artushock.apps.spillme.db.entities.ConditionsEntity
import com.artushock.apps.spillme.db.entities.FertilizerCareJoinEntity
import com.artushock.apps.spillme.db.entities.FertilizerEntity
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.db.entities.PlantTypeEntity

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
