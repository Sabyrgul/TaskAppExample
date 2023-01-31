package com.geektech.taskappexample.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geektech.taskappexample.databinding.FragmentProfileBinding
import com.geektech.taskappexample.utils.Preferences

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    var profileImageUri: Uri? = null

    private val profilePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding.profileImage.setImageURI(uri)
        profileImageUri = uri


    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            initViews()
        initListeners()

    }

    private fun initListeners() {
        binding?.profileImage?.setOnClickListener {
            profilePicker.launch("image/*")
        }
        binding?.btnSave?.setOnClickListener {
            val name=binding?.profileEditText?.text.toString()
            Preferences(requireContext()).setNameInserted(name)
          Preferences(requireContext()).setPictureInserted(profileImageUri!!)
        }
    }

    private fun initViews() {


        Preferences(requireContext()).getPictureInserted()?.let {
           Glide.with(requireContext()).load(it)
               .circleCrop()
               .into(binding?.profileImage!!)
        }

        Preferences(requireContext()).getNameInserted()?.let {
            binding?.profileEditText?.setText(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    _binding = null
  }
}