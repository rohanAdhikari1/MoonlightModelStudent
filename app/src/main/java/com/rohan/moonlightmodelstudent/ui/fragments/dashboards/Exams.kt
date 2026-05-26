package com.rohan.moonlightmodelstudent.ui.fragments.dashboards

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rohan.moonlightmodelstudent.R
import com.rohan.moonlightmodelstudent.databinding.ActivityDashboardsExamsBinding
import com.rohan.moonlightmodelstudent.ui.fragments.dashboards.exams.ExamsResult
import com.rohan.moonlightmodelstudent.ui.fragments.dashboards.exams.ExamsRoutine


class Exams : AppCompatActivity() {
    private lateinit var bind: ActivityDashboardsExamsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDashboardsExamsBinding.inflate(layoutInflater)
        setContentView(bind.root)
        clicklisten()
    }

    private fun clicklisten() {
        bind.result.setOnClickListener {
            val intent = Intent(this@Exams, ExamsResult::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.fadein,
                R.anim.fade_out
            )
        }
        bind.upcoming.setOnClickListener {
            val intent = Intent(this@Exams, ExamsRoutine::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.fadein,
                R.anim.fade_out
            )
        }
        bind.back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.fadein,
            R.anim.fade_out
        )
    }
}