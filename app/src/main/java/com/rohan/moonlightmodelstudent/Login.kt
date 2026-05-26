package com.rohan.moonlightmodelstudent

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.rohan.moonlightmodelstudent.api.Services
import com.rohan.moonlightmodelstudent.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var bind: ActivityLoginBinding
    var checkint:Int?=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
        checkinternet()
        clicklistners()
    }

    private fun clicklistners() {
        bind.scanqr.setOnClickListener{
            intent = Intent(this@Login,Scanner::class.java)
            startActivity(intent)
        }
        bind.login.setOnClickListener{
            val username = bind.username.text.toString()
            val password = bind.password.text.toString()
            if (username.isEmpty() || password.isEmpty()){
                if(password.isEmpty()){
                    bind.password.requestFocus()
                    bind.passwordlayout.error = "Invalid Password"
                }
                if(username.isEmpty()){
                    bind.username.requestFocus()
                    bind.usernamelayout.error = "Invalid Username"
                }
            }else{
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful){
                        Log.e("@@@",it.result.toString())
                        Services(this).login(username, password)
                    }
                })
            }
        }
    }

    private fun checkinternet(){
        val ck : CheckConnection = CheckConnection(application)
        ck.observe(this) {
            when (it) {
                1 -> {
                    if (checkint!! > 1){
                        val txt = "Internet is Back"
                        bind.checkinternet.text = txt
                        bind.checkinternet.setBackgroundColor(Color.parseColor("#108A10"))
                        bind.checkinternet.visibility = View.VISIBLE
                        Handler(Looper.getMainLooper()).postDelayed({
                            bind.checkinternet.visibility = View.GONE
                            checkint=1
                        }, 3000)
                    }
                }
                2 -> {
                    val txt = "Waiting for Internet"
                    bind.checkinternet.text = txt
                    bind.checkinternet.setBackgroundColor(Color.parseColor("#FDD408"))
                    bind.checkinternet.visibility = View.VISIBLE
                    checkint = 2
                }
                3 -> {
                    val txt = "No Internet Connection"
                    bind.checkinternet.text = txt
                    bind.checkinternet.setBackgroundColor(Color.parseColor("#C60606"))
                    bind.checkinternet.visibility = View.VISIBLE
                    checkint = 2
                }
                else -> {
                    val txt = "Internet Connection is Lost"
                    bind.checkinternet.text = txt
                    bind.checkinternet.setBackgroundColor(Color.parseColor("#C60606"))
                    bind.checkinternet.visibility = View.VISIBLE
                    checkint = 2
                }
            }
        }
    }
}