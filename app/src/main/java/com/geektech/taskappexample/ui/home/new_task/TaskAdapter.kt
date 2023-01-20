package com.geektech.taskappexample.ui.home.new_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.taskappexample.databinding.ItemTaskBinding

class TaskAdapter(private var tasks: MutableList<TaskModel>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    fun add(task: TaskModel){
        tasks.add(0,task)
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskModel) {
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.description
        }
    }


}

data class TaskModel(
    val title: String,
    val description: String
)