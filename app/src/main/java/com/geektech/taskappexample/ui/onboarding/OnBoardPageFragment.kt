package com.geektech.taskappexample.ui.onboarding

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentOnBoardPageBinding

class OnBoardPageFragment : Fragment() {
    companion object {
        const val IS_LAST_ARG = "is_last"
    }

 var binding: FragmentOnBoardPageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPageBinding.inflate(
            LayoutInflater.from(context), container, false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(BoardModel.ARG_KEY, BoardModel::class.java)
        } else {
            arguments?.getSerializable(BoardModel.ARG_KEY) as BoardModel
        }
        val isLast = arguments?.getBoolean(IS_LAST_ARG)

        if (data != null) {
            binding?.apply {
                root.setBackgroundResource(data.bgColor)
                ivBoardPicture.setImageResource(data.imageRes)
                tvTitle.text = data.title
                tvDesc.text = data.description

                if (isLast == true) {
                    btnNext.visibility = View.GONE
                    tvSkip.visibility = View.GONE
                    btnStart.visibility = View.VISIBLE
                }
            }

        }
    }
    fun initListeners(){
   binding?.apply {
       tvSkip.setOnClickListener {
           (parentFragment as OnBoardListeners).onSkipClicked()
       }
       btnNext.setOnClickListener {
           (parentFragment as OnBoardListeners).onNextClicked()
       }
       btnStart.setOnClickListener {
           (parentFragment as OnBoardListeners).onStartClicked()
       }
   }

    }
    interface OnBoardListeners{
        fun onSkipClicked()
        fun onNextClicked()
        fun onStartClicked()
    }
}
