package com.geektech.taskappexample.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentProfileBinding
import com.geektech.taskappexample.utils.Preferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
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
        binding?.btnSignOut?.setOnClickListener {

            Firebase.auth.signOut()

            Preferences(requireContext()).apply {
                setHaveSeenOnBoarding(false)
            }

            findNavController().navigate(R.id.authFragment)
        }
    }

    private fun initViews() {


        Preferences(requireContext()).getPictureInserted()?.let {
            Glide.with(requireContext()).load(it)
                .centerCrop()
                .into(binding?.profileImage!!)
        }

        Preferences(requireContext()).getNameInserted()?.let {
            binding?.profileEditText?.setText(it)
        }
    }

    override fun onDestroyView() {
        val name = binding?.profileEditText?.text.toString()
        Preferences(requireContext()).setNameInserted(name)
        profileImageUri?.let {
            Preferences(requireContext()).setPictureInserted(it)
        }
        super.onDestroyView()
        _binding = null
    }
}