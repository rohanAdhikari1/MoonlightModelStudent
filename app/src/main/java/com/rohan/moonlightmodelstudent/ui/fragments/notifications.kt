package com.rohan.moonlightmodelstudent.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.moonlightmodelstudent.databinding.FragmentNotificationsBinding


class notifications : Fragment() {
    private var binding: FragmentNotificationsBinding ? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding!!.root
        inite()
        return view
    }

    private fun inite() {
        binding!!.backnotif.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })
    }

    companion object {
        fun newInstance():notifications{
              return notifications()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}