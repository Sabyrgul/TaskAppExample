package com.geektech.taskappexample.ui.home.new_task

import androidx.room.*

data class TaskModel(
    val id: Int,
    val title: String,
    val description: String?,
    val imageUri: String? = null,
    val date: String?

)
@Entity(tableName="tasks")
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "desc")
    val description: String? = null,
    @ColumnInfo(name = "picture_uri")
    val pictureUri: String? = null,
    @ColumnInfo(name = "date")
    val date: String? = null,

    )
@Dao
interface TaskDao{
    @Insert
    fun insert(task:TaskEntity)

    @Query("DELETE FROM tasks WHERE id=:taskId")
    fun deleteBy(taskId:Long)

    @Query("SELECT*FROM tasks")
    suspend fun getAllSortedByDate():List<TaskEntity>

    @Query("SELECT*FROM tasks ORDER BY id DESC")
    suspend fun getAll():List<TaskEntity>

}