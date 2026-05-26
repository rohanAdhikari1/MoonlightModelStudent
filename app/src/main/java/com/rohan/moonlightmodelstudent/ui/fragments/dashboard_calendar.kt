package com.rohan.moonlightmodelstudent.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.moonlightmodelstudent.databinding.FragmentDashboardCalendarBinding
import com.rohan.nepalicalendar.DateClickListener
import com.rohan.nepalicalendar.enum.CalendarType
import com.rohan.nepalicalendar.enum.LocalizationType
import com.rohan.nepalicalendar.model.DateModel


class dashboard_calendar : Fragment() {
    private var binding: FragmentDashboardCalendarBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardCalendarBinding.inflate(inflater, container, false)
        val view = binding!!.root
        getcalendar()
        return view
    }

    private fun getcalendar() {
        val dateClickListener = object : DateClickListener {
            override fun onDateClick(dateModel: DateModel) {
                Log.d("d", "clicked date is ${dateModel.formattedAdDate}")
            }
        }
        binding!!.mCalendarView.setCalendarType(CalendarType.BS)
            .setLanguage(LocalizationType.NEPALI_NP)
            .setOnDateClickListener(dateClickListener)
            .build()
    }

    companion object{
        fun newInstance() :dashboard_calendar{
            return dashboard_calendar()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}