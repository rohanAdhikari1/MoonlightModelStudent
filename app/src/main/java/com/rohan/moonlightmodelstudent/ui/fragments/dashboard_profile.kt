package com.rohan.moonlightmodelstudent.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rohan.moonlightmodelstudent.adapters.ProfileTabAdapter
import com.rohan.moonlightmodelstudent.databinding.FragmentDashboardProfileBinding

class dashboard_profile : Fragment() {
    private var binding:FragmentDashboardProfileBinding?=null
    val tabs = arrayOf("PROFILE","DIGITAL CARD")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardProfileBinding.inflate(inflater, container, false)
        val view = binding!!.root
        initl(binding!!)
        return view
    }

    private fun initl(bind:FragmentDashboardProfileBinding) {
        val viewPager = bind.viewpager
        val tabLayout = bind.tabs
        val adapter = ProfileTabAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    companion object {
        fun newInstance() : dashboard_profile{
            return dashboard_profile()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}