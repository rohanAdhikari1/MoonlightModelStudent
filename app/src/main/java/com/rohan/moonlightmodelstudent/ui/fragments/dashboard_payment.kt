package com.rohan.moonlightmodelstudent.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.moonlightmodelstudent.R
import com.rohan.moonlightmodelstudent.databinding.FragmentDashboardPaymentBinding
import com.rohan.moonlightmodelstudent.ui.fragments.payments.payment_statements

class dashboard_payment : Fragment() {
    private var binding : FragmentDashboardPaymentBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardPaymentBinding.inflate(inflater, container, false)
        val view = binding!!.root
        clicklisten()
        return view
    }

    private fun clicklisten() {
        binding!!.payPayments.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(android.R.id.content, payment_statements.newInstance(), "buttons")
                .setCustomAnimations(R.anim.fadein, R.anim.fade_out, R.anim.fadein, R.anim.fade_out)
                .commit()
        }
        binding!!.creditbill.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(android.R.id.content, payment_statements.newInstance(), "buttons")
                .setCustomAnimations(R.anim.fadein, R.anim.fade_out, R.anim.fadein, R.anim.fade_out)
                .commit()
        }
        binding!!.statements.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(android.R.id.content, payment_statements.newInstance(), "buttons")
                .setCustomAnimations(R.anim.fadein, R.anim.fade_out, R.anim.fadein, R.anim.fade_out)
                .commit()
        }
        binding!!.deusummary.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(android.R.id.content, payment_statements.newInstance(), "buttons")
                .setCustomAnimations(R.anim.fadein, R.anim.fade_out, R.anim.fadein, R.anim.fade_out)
                .commit()
        }

    }

        fun newInstance() :dashboard_payment {
                    return dashboard_payment()
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}