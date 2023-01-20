package com.geektech.taskappexample.ui.home.new_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentNewTaskBinding

class NewTaskFragment : Fragment() {

    private var binding: FragmentNewTaskBinding?=null

    companion object {
        const val NEW_TASK_RESULT_KEY="new_task_result"
        const val NEW_TASK_TITLE_KEY="new_task_title"
        const val NEW_TASK_DESCRIPTION_KEY="new_task_desc"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentNewTaskBinding.inflate(LayoutInflater.from(context), container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }
 private fun initListeners(){
     binding?.btnSave?.setOnClickListener{
         val bundle= bundleOf(
             NEW_TASK_TITLE_KEY to binding?.etTitle?.text.toString(),
            NEW_TASK_DESCRIPTION_KEY to binding?.etDescription?.text.toString()
         )
         setFragmentResult(
             NEW_TASK_RESULT_KEY,
             bundle
         )
         findNavController().navigateUp()
     }

 }
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}