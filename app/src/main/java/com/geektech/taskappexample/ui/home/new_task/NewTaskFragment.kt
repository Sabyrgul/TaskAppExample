package com.geektech.taskappexample.ui.home.new_task

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geektech.taskappexample.databinding.FragmentNewTaskBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class NewTaskFragment : Fragment() {

    private var binding: FragmentNewTaskBinding? = null
    var date: String? = null
    private var imageUri: Uri? = null

    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding?.ivPicture?.setImageURI(uri)
        imageUri = uri
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewTaskBinding.inflate(LayoutInflater.from(context), container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

        val cal = Calendar.getInstance()
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val year = cal.get(Calendar.YEAR)

        date = "$day.$month.$year"
    }

    private fun initListeners() {
        binding?.btnSave?.setOnClickListener {

            val model = TaskModel(
                title = binding?.etTitle?.text.toString(),
                description = binding?.etDescription?.text?.toString(),
                imageUri = imageUri?.toString(),
                date = this.date
            )
            binding?.layoutTitle?.visibility = View.GONE
            binding?.layoutDescription?.visibility = View.GONE
            binding?.ivPicture?.visibility = View.GONE
            binding?.btnSave?.visibility = View.GONE
            binding?.progressCircularBar?.visibility = View.VISIBLE

            Firebase.firestore.collection("tasks").add(
                model
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Task created", Toast.LENGTH_LONG).show()
                }
                binding?.progressCircularBar?.visibility = View.GONE

                findNavController().navigateUp()
            }

        }

        binding?.ivPicture?.setOnClickListener {
            imagePicker.launch("image/*")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}