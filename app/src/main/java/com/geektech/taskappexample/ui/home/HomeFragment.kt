package com.geektech.taskappexample.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.taskappexample.MainApplication
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentHomeBinding
import com.geektech.taskappexample.ui.home.new_task.TaskAdapter
import com.geektech.taskappexample.ui.home.new_task.TaskEntity
import com.geektech.taskappexample.ui.home.new_task.TaskModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val taskAdapter = TaskAdapter(mutableListOf())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.burger_menu, menu)
    }

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
        showItems()
    }


    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_date -> {
                lifecycleScope.launch {
                    val tasks = MainApplication?.appDataBase?.taskDao?.getAllSortedByDate()
                    sortedItems(tasks)
                }
                true
            }
            R.id.sort_by_alphabet -> {

                lifecycleScope.launch {
                    val tasks =
                        MainApplication?.appDataBase?.taskDao?.getAll()?.sortedBy { it.title }
                    sortedItems(tasks)

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortedItems(tasks: List<TaskEntity>?) {
        val taskModels = tasks?.map {
            TaskModel(
                id = it.id.toInt(),
                title = it.title,
                description = it.description,
                imageUri = it.pictureUri,
                date = it.date
            )
        }
        taskModels?.let {
            taskAdapter.renew(it)
        }
    }

    private fun initViews() {

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        taskAdapter.onDelete = {
            AlertDialog.Builder(requireContext())
                .setTitle("Warning!")
                .setMessage("Are you sure to delete?")
                .setPositiveButton("OK") { _, _ ->
                    MainApplication.appDataBase?.taskDao?.deleteBy(
                        it.toLong()
                    )
                }
                .setNeutralButton("Cancel", null)
                .show()
            showItems()
        }
    }

    private fun showItems() {

        lifecycleScope.launch {
            val tasks = MainApplication?.appDataBase?.taskDao?.getAll()
            val taskModels = tasks?.map {
                TaskModel(
                    id = it.id.toInt(),
                    title = it.title,
                    description = it.description,
                    imageUri = it.pictureUri,
                    date = it.date
                )
            }
            taskModels?.let {
                taskAdapter.renew(it)
            }

        }

    }

    private fun initListeners() {


        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}