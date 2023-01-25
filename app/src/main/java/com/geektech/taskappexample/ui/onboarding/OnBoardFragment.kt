package com.geektech.taskappexample.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.geektech.taskappexample.R
import com.geektech.taskappexample.databinding.FragmentOnBoardBinding
import com.geektech.taskappexample.databinding.FragmentOnBoardPageBinding
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

class OnBoardFragment : Fragment(), OnBoardPageFragment.OnBoardListeners {

    private var binding: FragmentOnBoardBinding? = null
    private var boardAdapter: OnBoardAdapter? = null
    private var boardModels = listOf(
        BoardModel(
            bgColor = R.color.bg_color1,
            imageRes = R.drawable.ic_onboarding1,
            title = "To-do list!",
            description = "Here you can write down\nsomething important or make a \nschedule for tomorrow :)"
        ),
        BoardModel(
            bgColor = R.color.bg_color2,
            imageRes = R.drawable.ic_onboarding2,
            title = "Share your crazy\nidea ^_^",
            description = "You can easily share with your \nreport, list or schedule and it's \nconvenient"
        ),
        BoardModel(
            bgColor = R.color.bg_color3,
            imageRes = R.drawable.ic_onboarding3,
            title = "Flexibility",
            description = "Your note with you at home, at work, even at the resort"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(LayoutInflater.from(context), container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        boardAdapter = OnBoardAdapter(
            childFragmentManager, lifecycle, boardModels
        )

        binding?.viewpager?.adapter = boardAdapter
    }

    override fun onSkipClicked() {
        binding?.viewpager?.setCurrentItem(boardModels.lastIndex, true)
    }

    override fun onNextClicked() {
        val pager = binding?.viewpager
        pager?.setCurrentItem(pager.currentItem + 1, true)
    }

    override fun onStartClicked() {
        findNavController().navigate(R.id.navigation_home)
    }

    override fun getViewPager(): ViewPager2 {
        return binding?.viewpager!!
    }
}
