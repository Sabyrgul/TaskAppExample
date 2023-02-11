package com.geektech.taskappexample.ui.home.new_task


data class TaskModel(
    val id: Int,
    val title: String,
    val description: String?,
    val imageUri: String? = null,
    val date: String?

)

