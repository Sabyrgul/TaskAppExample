package com.geektech.taskappexample

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geektech.taskappexample.data.SessionDao
import com.geektech.taskappexample.data.SessionEntity
import com.geektech.taskappexample.data.TaskDao
import com.geektech.taskappexample.data.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        SessionEntity::class
    ],
    version = 1,

    )
abstract class AppDataBase : RoomDatabase() {

    abstract val taskDao: TaskDao
    abstract val sessionDao:SessionDao
}