package com.geektech.taskappexample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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