package com.geektech.taskappexample.ui.home.new_task

import com.google.firebase.firestore.DocumentId


data class TaskModel(
    @DocumentId
    val id: String="",
    val title: String="",
    val description: String?=null,
    val imageUri: String? = null,
    val date: String?=null
)

