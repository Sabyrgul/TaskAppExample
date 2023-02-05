package com.geektech.taskappexample.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

  private var binding:FragmentAuthBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAuthBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}