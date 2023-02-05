package com.geektech.taskappexample.ui.onboarding

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardAdapter(
    fm: FragmentManager, lifecycle: Lifecycle,
    private val boardModels: List<BoardModel>
) :
    FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount() = boardModels.size

    override fun createFragment(position: Int): Fragment {

        val isLast = boardModels.lastIndex == position
        val bundle = bundleOf(
            BoardModel.ARG_KEY to boardModels[position],
            OnBoardPageFragment.IS_LAST_ARG to isLast
        )

        val fragment = OnBoardPageFragment()
        fragment.arguments = bundle
        return fragment
    }
}

data class BoardModel(
    val bgColor: Int,
    val imageRes: Int,
    val title: String,
    val description: String
) : java.io.Serializable {
    companion object {

        const val ARG_KEY = "board"
    }
}