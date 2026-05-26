package com.rohan.moonlightmodelstudent.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.moonlightmodelstudent.R

class Profile_Card_View : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile__card__view, container, false)
    }

    companion object {
        fun newInstance():Profile_Card_View{
            return Profile_Card_View()
            }
    }
}