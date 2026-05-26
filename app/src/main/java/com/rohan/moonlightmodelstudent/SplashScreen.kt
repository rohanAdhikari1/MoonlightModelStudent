package com.rohan.moonlightmodelstudent

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.rohan.moonlightmodelstudent.api.Tokenizer
import com.rohan.moonlightmodelstudent.databinding.ActivitySplashScreenBinding
import com.rohan.moonlightmodelstudent.ui.Dashboard


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var bind: ActivitySplashScreenBinding
    private var appUpdate : AppUpdateManager? = null
    private val requestCode = 100
    init{
        System.loadLibrary("keys")
    }
    private external fun getApikey():String
    private external fun getApiurl():String
    private val CHANNEL_ID = "1001"
    private var destination: Int? = null
    private var hasactivity = false
//    private var initialize = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notif()
        hidebar()
        checkupdate()
        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey("destination")) {
                destination = bundle.getInt("destination")
                hasactivity = true
            }
        }
        bind= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        bind.imageView.animation = animation
        Handler(Looper.getMainLooper()).postDelayed({
            performtasks()
        }, 3000)
    }

    private fun notif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = "Notices"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
//            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }


    private  fun performtasks() {
        intent = if (!hasactivity){
            val token = Tokenizer.getEncryptedShredPrefs(this).getString("token","").toString()
            val islogged = Tokenizer.getEncryptedShredPrefs(this).getBoolean("islogged",false)
            if (islogged && token != ""){
                Intent(this@SplashScreen,Dashboard::class.java)
            }else{
                Intent(this@SplashScreen,Dashboard::class.java)
            }
        }else{
            Intent(this@SplashScreen,Login::class.java)
        }
        startActivity(intent)
        finish()
    }

    private fun hidebar() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun checkupdate() {
        appUpdate = AppUpdateManagerFactory.create(this)
        appUpdate?.appUpdateInfo?.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                appUpdate?.startUpdateFlowForResult(it,AppUpdateType.IMMEDIATE,this,requestCode)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }
    private fun inProgressUpdate(){
        appUpdate = AppUpdateManagerFactory.create(this)
        appUpdate?.appUpdateInfo?.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdate?.startUpdateFlowForResult(it,AppUpdateType.IMMEDIATE,this,requestCode)
            }
        }
    }
}