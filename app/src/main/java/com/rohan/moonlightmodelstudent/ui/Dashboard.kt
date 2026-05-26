package com.rohan.moonlightmodelstudent.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import com.rohan.moonlightmodelstudent.R
import com.rohan.moonlightmodelstudent.databinding.ActivityDashboardBinding
import com.rohan.moonlightmodelstudent.ui.fragments.menus
import com.rohan.moonlightmodelstudent.ui.fragments.notifications

class Dashboard : AppCompatActivity() {
    private lateinit var bind: ActivityDashboardBinding
    private lateinit var navController: NavController
    private var oldfragid:Int = 3
    companion object {
        val DASHBOARD_ITEM = R.id.dashboard_dashboard
        val PROFILE_ITEM = R.id.dashboard_profile
        val PAYMENT_ITEM = R.id.dashboard_payment
        val CALENDAR_ITEM = R.id.dashboard_calendar
        val LEARNING_ITEM = R.id.dashboard_elearn
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dashboardframe) as NavHostFragment
        navController = navHostFragment.navController
        setUpBottomNavigation()
        clicklisten()
    }

    private fun clicklisten() {
        bind.menus.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left)
                .add(android.R.id.content, menus.newInstance(), "menus")
                .commit()
        }
        bind.notifications.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left)
                .add(android.R.id.content, notifications.newInstance(), "menus")
                .commit()
        }
    }

    private fun setUpBottomNavigation() {
            val bottomNavigationItems = mutableListOf(
                CurvedBottomNavigation.Model(
                    LEARNING_ITEM,
                    getString(R.string.learn_frag),
                    R.drawable.e_book
                ),
                CurvedBottomNavigation.Model(
                    CALENDAR_ITEM,
                    getString(R.string.calen_frag),
                    R.drawable.ic_calendar
                ),
                CurvedBottomNavigation.Model(
                    DASHBOARD_ITEM,
                    getString(R.string.dash_frag),
                    R.drawable.ic_dashboard
                ),
                CurvedBottomNavigation.Model(PAYMENT_ITEM, getString(R.string.pay_frag), R.drawable.ic_payments),
                CurvedBottomNavigation.Model(
                    PROFILE_ITEM,
                    getString(R.string.prof_frag),
                    R.drawable.ic_person
                ),
            )
            bind.buttommnav.apply {
                bottomNavigationItems.forEach { add(it) }
                setOnClickMenuListener {
                    setfragment(it.id)
                }
                setupNavController(navController)
                oldfragid = DASHBOARD_ITEM
            }
    }
    private fun setfragment(id:Int){
        when(id){
            LEARNING_ITEM->{
                val name = "E-learning"
                bind.toolbarText.text = name
                when(oldfragid){
                    CALENDAR_ITEM->navController.navigate(R.id.action_dashboard_calendar_to_dashboard_elearn)
                    DASHBOARD_ITEM->navController.navigate(R.id.action_dashboard_dashboard_to_dashboard_elearn)
                    PAYMENT_ITEM->navController.navigate(R.id.action_dashboard_payment_to_dashboard_elearn)
                    PROFILE_ITEM->navController.navigate(R.id.action_dashboard_profile_to_dashboard_elearn)
                }
            }
            CALENDAR_ITEM->{
                val name = "Calendar"
                bind.toolbarText.text = name
                when(oldfragid){
                    LEARNING_ITEM->navController.navigate(R.id.action_dashboard_elearn_to_dashboard_calendar)
                    DASHBOARD_ITEM->navController.navigate(R.id.action_dashboard_dashboard_to_dashboard_calendar)
                    PAYMENT_ITEM->navController.navigate(R.id.action_dashboard_payment_to_dashboard_calendar)
                    PROFILE_ITEM->navController.navigate(R.id.action_dashboard_profile_to_dashboard_calendar)
                }
            }
            DASHBOARD_ITEM->{
                val name = "Dashboard"
                bind.toolbarText.text = name
                when(oldfragid){
                    LEARNING_ITEM->navController.navigate(R.id.action_dashboard_elearn_to_dashboard_dashboard)
                    CALENDAR_ITEM->navController.navigate(R.id.action_dashboard_calendar_to_dashboard_dashboard)
                    PAYMENT_ITEM->navController.navigate(R.id.action_dashboard_payment_to_dashboard_dashboard)
                    PROFILE_ITEM->navController.navigate(R.id.action_dashboard_profile_to_dashboard_dashboard)
                }
            }
            PAYMENT_ITEM->{
                val name = "Payments"
                bind.toolbarText.text = name
                                when(oldfragid){
                    LEARNING_ITEM->navController.navigate(R.id.action_dashboard_elearn_to_dashboard_payment)
                    CALENDAR_ITEM->navController.navigate(R.id.action_dashboard_calendar_to_dashboard_payment)
                    DASHBOARD_ITEM->navController.navigate(R.id.action_dashboard_dashboard_to_dashboard_payment)
                    PROFILE_ITEM->navController.navigate(R.id.action_dashboard_profile_to_dashboard_payment)
                }
            }
            PROFILE_ITEM->{
                val name = "Profile"
                bind.toolbarText.text = name
                when(oldfragid){
                    LEARNING_ITEM->navController.navigate(R.id.action_dashboard_elearn_to_dashboard_profile)
                    CALENDAR_ITEM->navController.navigate(R.id.action_dashboard_calendar_to_dashboard_profile)
                    DASHBOARD_ITEM->navController.navigate(R.id.action_dashboard_dashboard_to_dashboard_profile)
                    PAYMENT_ITEM->navController.navigate(R.id.action_dashboard_payment_to_dashboard_profile)
                }
            }
        }
        oldfragid= id
    }


    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag("menus")
        if (fragment != null) supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right).remove(fragment).commit()
        val fragment2: Fragment? = supportFragmentManager.findFragmentByTag("buttons")
        if (fragment2 != null) supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fade_out).remove(fragment2).commit()
        if(fragment == null && fragment2 == null){
            Toast.makeText(this,"I am not on fragment",Toast.LENGTH_SHORT).show();
            if (navController.currentDestination!!.id == DASHBOARD_ITEM)
                super.onBackPressed()
            else {
                navController.popBackStack(R.id.dashboard_dashboard, false)
//                when (navController.currentDestination!!.id) {
//                    LEARNING_ITEM -> {
//                        navController.popBackStack(R.id.dashboard_dashboard, false)
//                    }
//                    CALENDAR_ITEM -> {
//                        navController.popBackStack(R.id.dashboard_dashboard, false)
//                    }
//                    PAYMENT_ITEM -> {
//                        navController.popBackStack(R.id.dashboard_dashboard, false)
//                    }
//                    PROFILE_ITEM -> {
//                        navController.popBackStack(R.id.dashboard_dashboard, false)
//                    }
//                }
            }
            oldfragid = DASHBOARD_ITEM
        }
    }
}