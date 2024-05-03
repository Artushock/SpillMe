package com.artushock.database.dbcallback

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val dbCallback = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL(
            """
            insert into `plant_type` (id, name, description, careId, conditionsId)
            values (0, "Test type", "Test description", 0, 0);
        """.trimIndent()
        )

        db.execSQL(
            """
            insert into `care_frequency` (id, wateringFrequency, sprayingFrequency, rubbingFrequency, transplantingFrequency, bathingFrequency)
            values (0, 12, 12, 12, 12, 12)        
        """.trimIndent()
        )

        db.execSQL(
            """
            insert into `conditions` (id, minTemperature, maxTemperature, minHumidity, maxHumidity, lightningType, soilId)
            values (0, 0, 100, 50, 90, 0, 0) 
        """.trimIndent()
        )
    }
}