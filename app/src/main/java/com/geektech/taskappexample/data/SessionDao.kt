package com.geektech.taskappexample.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SessionDao {
    @Insert
    suspend fun save(item:SessionEntity)

    @Query("SELECT * FROM session")
    suspend fun getSession():SessionEntity?

    @Query("DELETE FROM session")
    suspend fun deleteSession()
}