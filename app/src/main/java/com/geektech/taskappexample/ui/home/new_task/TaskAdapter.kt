package com.geektech.taskappexample.ui.home.new_task

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.taskappexample.databinding.ItemTaskBinding

class TaskAdapter(private var tasks: MutableList<TaskModel> = ArrayList()) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var onDelete: ((String) -> Unit)? = null


    fun renew(newList: List<TaskModel>) {
        tasks = newList.toMutableList()
        notifyDataSetChanged()
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
            binding.tvDate.text = task.date

//            binding.root.setOnLongClickListener {
//                onDelete?.invoke(task.id)
//                true
//            }

            if (task.imageUri != null) {
                binding.ivPicture.setImageURI(Uri.parse(task.imageUri))
                binding.ivPicture.visibility = View.VISIBLE
            }
            else binding.ivPicture.visibility=View.GONE
        }
    }

}

