package com.rohan.moonlightmodelstudent.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.moonlightmodelstudent.api.Services
import com.rohan.moonlightmodelstudent.databinding.FragmentMenusBinding


class menus : Fragment() {
    private var binding: FragmentMenusBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenusBinding.inflate(inflater, container, false)
        val view = binding!!.root
        inite(binding!!)
        return view
    }

    private fun inite(bind:FragmentMenusBinding) {
        val pInfo = requireActivity().packageManager.getPackageInfo(
            requireActivity().packageName, 0
        )
        val versionname = pInfo.versionName
        val versionnum = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            "("+pInfo.longVersionCode+")"
        } else {
            ""
        }
        val txt = "V $versionname $versionnum"
        bind.versionview.text=txt
        bind.backmenu.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressed()
        })
        bind.logout.setOnClickListener(View.OnClickListener {
            Services(requireContext()).logout()
        })
        bind.developer.setOnClickListener(View.OnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://www.rohanadhikari.com.np/")
            )
            startActivity(viewIntent)
        })
    }

    companion object {
        fun newInstance(): menus {
            return menus()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}