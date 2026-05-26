package com.rohan.moonlightmodelstudent.ui.fragments.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.moonlightmodelstudent.databinding.FragmentPaymentsStatementsBinding

class payment_statements : Fragment() {
    private var binding : FragmentPaymentsStatementsBinding ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentsStatementsBinding.inflate(inflater, container, false)
        val view = binding!!.root
        initl()
        return view
    }

    private fun initl() {
        binding!!.backstatements.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        fun newInstance() : payment_statements {
               return payment_statements()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}