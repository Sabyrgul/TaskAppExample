package com.geektech.taskappexample

import android.app.Application
import androidx.room.Room

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        appDataBase = Room.databaseBuilder(
            this,
            AppDataBase::class.java,
            "task_db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        var appDataBase: AppDataBase? = null
    }
}