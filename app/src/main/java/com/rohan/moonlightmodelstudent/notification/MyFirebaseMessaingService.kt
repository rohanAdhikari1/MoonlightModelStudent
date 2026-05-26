package com.rohan.moonlightmodelstudent.notification

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rohan.moonlightmodelstudent.R


class MyFirebaseMessaingService :FirebaseMessagingService() {
    private val channelId ="1001"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        getFirebaseMessage(message.notification!!.title!!,message.notification!!.body!!)
    }

    private fun getFirebaseMessage(title:String, msg:String){
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
       val builder = NotificationCompat.Builder(this,channelId)
           .setSmallIcon(R.drawable.ic)
           .setContentTitle(title)
           .setLights(Color.RED, 3000, 3000)
           .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000 ))
           .setSound(defaultSoundUri)
           .setColor(getColor(R.color.prime))
           .setContentText(msg)
           .setAutoCancel(true)

        val manager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        manager.notify(101,builder.build())
    }
}