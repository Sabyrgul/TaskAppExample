package com.geektech.taskappexample.ui.home.new_task

//import android.arch.persistence.room.*

data class TaskModel(
    val title: String,
    val description: String,
    val imageUri: String? = null,
    val date:String
)
//@Entity(tableName="tasks")
//data class TaskEntity(
//    @PrimaryKey(autoGenerate=true)
//    val id:Long=0,
//    @ColumnInfo(name="title")
//    val title: String,
//    @ColumnInfo(name="desc")
//    val description: String?=null,
//    @ColumnInfo(name="picture_uri")
//    val pictureUri:String?=null,
//
//)
//@Dao
//interface TaskDao{
//    @Insert
//    fun insert(task:TaskEntity)
//
//    @Query("SELECT*FROM tasks")
//    fun getAll():List<TaskEntity>
//}