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
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    lateinit var profileUri:Uri
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profilePick = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            profileUri = result.data!!.data!!
            Glide.with(this).load(profileUri).transform(
                CenterCrop(), RoundedCorners(200)
            ).into(binding.profileImage)
            //binding.profileImage.setImageURI(profileUri)
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            profilePick.launch(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}