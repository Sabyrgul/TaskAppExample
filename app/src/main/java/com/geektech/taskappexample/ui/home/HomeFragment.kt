package com.geektech.taskappexample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentHomeBinding
import com.geektech.taskappexample.ui.home.new_task.NewTaskFragment
import com.geektech.taskappexample.ui.home.new_task.TaskAdapter
import com.geektech.taskappexample.ui.home.new_task.TaskModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var taskAdapter = TaskAdapter(mutableListOf())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }
    }

    private fun initListeners() {

        setFragmentResultListener(
            NewTaskFragment.NEW_TASK_RESULT_KEY,
        ) { _, data ->
            val title = data.getString(NewTaskFragment.NEW_TASK_TITLE_KEY)
            val description = data.getString(NewTaskFragment.NEW_TASK_DESCRIPTION_KEY)
            val imageUri = data.getString(NewTaskFragment.NEW_TASK_IMAGE_URI_KEY)
            val date=data.getString(NewTaskFragment.NEW_TASK_DATE_KEY)
            if (title != null) {
                val taskmodel = TaskModel(title, description ?: "", imageUri,date?:"")
                taskAdapter.add(taskmodel)
            }
        }

        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}