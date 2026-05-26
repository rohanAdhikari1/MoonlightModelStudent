package com.rohan.moonlightmodelstudent.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rohan.moonlightmodelstudent.ui.fragments.Profile_Card_View
import com.rohan.moonlightmodelstudent.ui.fragments.Profile_Detail_View

class ProfileTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val NUM_TABS = 2
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return Profile_Card_View.newInstance()
        }
        return Profile_Detail_View.newInstance()
    }
}