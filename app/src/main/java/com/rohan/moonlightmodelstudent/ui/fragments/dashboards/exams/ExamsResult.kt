package com.rohan.moonlightmodelstudent.ui.fragments.dashboards.exams

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.rohan.moonlightmodelstudent.R
import com.rohan.moonlightmodelstudent.api.RetrofitInstance
import com.rohan.moonlightmodelstudent.databinding.ActivityExamsResultBinding
import com.rohan.moonlightmodelstudent.model.SessionsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamsResult : AppCompatActivity() {
    private lateinit var bind: ActivityExamsResultBinding
    private val items = emptyArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityExamsResultBinding.inflate(layoutInflater)
        setContentView(bind.root)
        clicklisten()
        initl();
    }

    private fun initl() {
            RetrofitInstance(this).apiInterface.getsessions("sessions").enqueue(object : Callback<List<SessionsModel>?> {
                override fun onResponse(
                    call: Call<List<SessionsModel>?>,
                    response: Response<List<SessionsModel>?>
                ) {
                    Log.e("@@@",response.body().toString())
                }

                override fun onFailure(call: Call<List<SessionsModel>?>, t: Throwable) {
                    Log.e("@@@", t.localizedMessage!!.toString())
                }
            })
        val arrayAdapter: ArrayAdapter<*>
        val list = arrayOf(
            "Cristiano Ronaldo",
            "Messi",
            "Neymar",
            "Isco",
            "Hazard",
            "Mbappe",
            "Hazard",
            "Ziyech",
            "Suarez"
        )
        arrayAdapter = ArrayAdapter(
            this,
            R.layout.exams_items, list
        )
        bind.yearsselect.setAdapter(arrayAdapter)
        bind.yearsselect.setText(bind.yearsselect.adapter.getItem(0).toString(), false);
    }

    private fun clicklisten() {
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