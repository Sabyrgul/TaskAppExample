package com.geektech.taskappexample.ui.home

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.taskappexample.MainApplication
import com.geektech.taskappexample.R
import com.geektech.taskappexample.data.TaskEntity
import com.geektech.taskappexample.databinding.FragmentHomeBinding
import com.geektech.taskappexample.ui.home.new_task.TaskAdapter
import com.geektech.taskappexample.ui.home.new_task.TaskModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        initViews()
        initListeners()
        //showItems()
    }




    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissions() {

        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}
        launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.sort_by_date -> {
               sortedItemsByDate()
                true
            }
            R.id.sort_by_alphabet -> {

               sortedItemsByAlphabet()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortedItemsByDate() {

        Firebase.firestore.collection("tasks")
            .addSnapshotListener { value, _ ->
                val tasks=value?.documents?.map {
                    it.toObject(TaskModel::class.java)
                }
                tasks?.sortedBy { id }
                tasks?.let {
                    taskAdapter.renew(it as List<TaskModel>)
                }
            }
    }
    private fun sortedItemsByAlphabet() {

        Firebase.firestore.collection("tasks")
            .addSnapshotListener { value, _ ->
                val tasks=value?.documents?.map {
                    it.toObject(TaskModel::class.java)
                }
                tasks?.sortedBy { it?.title }
                tasks?.let {
                    taskAdapter.renew(it as List<TaskModel>)
                }
            }
    }


    private fun initViews() {

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        taskAdapter.onDelete = {id ->
            AlertDialog.Builder(requireContext())
                .setTitle("Warning!")
                .setMessage("Are you sure to delete?")
                .setPositiveButton("OK") { _, _ ->
                  Firebase.firestore.collection("tasks")
                      .document(id)
                      .delete()
                      .addOnCompleteListener { task ->
                          if(task.isSuccessful){
                              Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show()
                          }

                      }
                }
                .setNeutralButton("Cancel", null)
                .show()
        }
    }



    private fun showItems() {
//
//        lifecycleScope.launch {
//            val tasks = MainApplication?.appDataBase?.taskDao?.getAll()
//            sortedItems(tasks)
//        }
        Firebase.firestore.collection("tasks")
            .addSnapshotListener { value, _ ->
                val tasks=value?.documents?.map {
                    it.toObject(TaskModel::class.java)
                }
                tasks?.let {
                    taskAdapter.renew(it as List<TaskModel>)
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