package com.rohan.moonlightmodelstudent.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohan.moonlightmodelstudent.R
import com.rohan.moonlightmodelstudent.adapters.NoticeListAdapter
import com.rohan.moonlightmodelstudent.databinding.FragmentDashboardDashboardBinding
import com.rohan.moonlightmodelstudent.model.NoticeModel
import com.rohan.moonlightmodelstudent.ui.fragments.dashboards.Exams


class dashboard_dashboard : Fragment() {
    private var binding:FragmentDashboardDashboardBinding ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardDashboardBinding.inflate(layoutInflater, container, false)
        val view = binding!!.root
        initl()
        clicklisten()
        return view
    }

    private fun initl() {
        binding!!.noticeAnnounceRecycle.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        val data = ArrayList<NoticeModel>()
        for (i in 1..4) {
            data.add(NoticeModel(1, "Item " + i))
        }
        val adapter = NoticeListAdapter(data)
        binding!!.noticeAnnounceRecycle.adapter = adapter
    }

    private fun clicklisten() {
        binding!!.exams.setOnClickListener {
            val intent = Intent(requireContext(), Exams::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(
                R.anim.fadein,
                R.anim.fade_out
            )
        }
    }

    companion object {
        fun newInstance() :dashboard_dashboard {
               return dashboard_dashboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}