package com.artushock.apps.spillme.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artushock.apps.spillme.db.dao.PlantDao
import com.artushock.apps.spillme.db.entities.PlantEntity

@Database(
    entities = [PlantEntity::class],
    version = 1
)
abstract class SpillMeDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
}
