package com.geektech.taskappexample.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao{
    @Insert
    fun insert(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id=:taskId")
    fun deleteBy(taskId:Long)

    @Query("SELECT*FROM tasks")
    suspend fun getAllSortedByDate():List<TaskEntity>

    @Query("SELECT*FROM tasks ORDER BY id DESC")
    suspend fun getAll():List<TaskEntity>

}