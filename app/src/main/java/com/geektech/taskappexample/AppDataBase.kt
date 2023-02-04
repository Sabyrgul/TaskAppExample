package com.geektech.taskappexample

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geektech.taskappexample.ui.home.new_task.TaskDao
import com.geektech.taskappexample.ui.home.new_task.TaskEntity

@Database (
    entities = [
  TaskEntity::class
    ],
    version=1,

)
    abstract class AppDataBase:RoomDatabase(){

        abstract val taskDao:TaskDao
}