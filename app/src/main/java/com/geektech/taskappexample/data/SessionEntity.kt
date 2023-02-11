package com.geektech.taskappexample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey(autoGenerate = false)
    val phoneNumber: String
)
