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
        // initViews()
        binding.profileImage.setOnClickListener {
            profilePicker.launch("image/*")
        }
    }

    private fun initViews() {

        val preferences = Preferences(requireContext())
        if (preferences.getNameInserted() != "1") binding.profileEditText.setText(preferences.getNameInserted())

        if (preferences.getPictureInserted() != "1") binding.profileImage.setImageURI(
            Uri.parse(
                preferences.getPictureInserted()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val preferences = Preferences(requireContext())
        if (binding.profileEditText.text.toString() != "") preferences.setNameInserted(binding.profileEditText.text.toString())
        if (profileImageUri != null) preferences.setPictureInserted(profileImageUri.toString())
        _binding = null
    }
}