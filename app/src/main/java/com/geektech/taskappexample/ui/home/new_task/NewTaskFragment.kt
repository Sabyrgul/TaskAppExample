package com.geektech.taskappexample.ui.home.new_task

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentNewTaskBinding
import java.util.Calendar

class NewTaskFragment : Fragment() {

    private var binding: FragmentNewTaskBinding? = null
    var date: String? = null
    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding?.ivPicture?.setImageURI(uri)
        imageUri = uri
    }

    private var imageUri: Uri? = null

    companion object {
        const val NEW_TASK_RESULT_KEY = "new_task_result"
        const val NEW_TASK_TITLE_KEY = "new_task_title"
        const val NEW_TASK_IMAGE_URI_KEY = "new_task_image"
        const val NEW_TASK_DESCRIPTION_KEY = "new_task_desc"
        const val NEW_TASK_DATE_KEY = "new_task_date"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        binding?.ivPicture?.setOnClickListener {
            imagePicker.launch("image/*")
        }
    }

    private fun initListeners() {
        binding?.btnSave?.setOnClickListener {
            val bundle = bundleOf(
                NEW_TASK_TITLE_KEY to binding?.etTitle?.text.toString(),
                NEW_TASK_DESCRIPTION_KEY to binding?.etDescription?.text.toString(),
                NEW_TASK_DATE_KEY to date
            )
            if (imageUri != null) {
                bundle.putString(NEW_TASK_IMAGE_URI_KEY, imageUri.toString())
            }
            setFragmentResult(
                NEW_TASK_RESULT_KEY,
                bundle
            )
            findNavController().navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}