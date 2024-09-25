package com.artushock.apps.spillme.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Delete
    suspend fun delete(entity: T)

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplaceId(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAbort(entity: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(entity: T)

    @Update(onConflict = OnConflictStrategy.NONE)
    suspend fun update(entity: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReplace(entity: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateAbort(entity: T)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateIgnore(entity: T)
}