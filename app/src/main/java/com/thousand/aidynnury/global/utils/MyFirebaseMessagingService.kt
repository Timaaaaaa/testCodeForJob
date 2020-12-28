package com.thousand.aidynnury.global.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.thousand.aidynnury.R
import com.thousand.aidynnury.main.presentation.activity.MainActivity


class MyFirebaseMessagingService: FirebaseMessagingService()  {

    companion object {
        const val TAG = "FMService"
    }

    private lateinit var notificationManager: NotificationManager

    private val ADMIN_CHANNEL_ID = "Aidynnury"


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i(TAG, "Received Token ${p0}")
    }


    override fun onMessageReceived(message: RemoteMessage) {

        val permSound = true
        val permPush = true
        val type = ""
        val id = ""


        Log.i(TAG, "Received message")
        Log.i(TAG, "Received data: ${message.data}")
        Log.i(TAG, "Received not data : ${message.notification?.body}")

        if (!message.data.isNullOrEmpty()) {
            val title = message.data["title"]
            val body = message.data["body"]
            val type= message.data["type"]!!
            val id = message.data["id"]!!

            Log.i(TAG, "Sound perm: $permSound")
            Log.i(TAG, "Push perm: $permPush")
            Log.i(TAG, "Title: $title")
            Log.i(TAG, "Body: $body")

            if (!title.isNullOrEmpty() && !body.isNullOrEmpty()) {
                setNotification(title, body, type, id,  permPush , permSound)
                return
            }
        }

        if (message.notification != null) {

            Log.i(TAG, message.notification.toString())
            message.notification?.body?.let { message.notification?.title?.let { it1 ->

                Log.i(TAG, "$it1 +$it")
                setNotification(
                    it1, it,type,id,  permPush , permSound)
                return
            } }
        }
    }

    private fun setNotification(
        title: String,
        body: String,
        type : String,
        id : String,
        permPush: Boolean,
        permAudio: Boolean
    ) {


    if (permPush) {


        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK )

        intent.putExtra("title", title)
        intent.putExtra("type", type)
        intent.putExtra("id", id)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannels()
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationId = 1000

        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
            .setSmallIcon(R.drawable.example2)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setLights(Color.WHITE, 2000, 3000)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        if (permAudio) {
            notificationBuilder.setSound(defaultSoundUri)
        }


        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannels() {
        val adminChannelName = getString(R.string.notifications_admin_channel_name)
        val adminChannelDescription = getString(R.string.notifications_admin)

        val adminChannel: NotificationChannel

        notificationManager.deleteNotificationChannel(ADMIN_CHANNEL_ID)

        adminChannel = NotificationChannel(
            ADMIN_CHANNEL_ID,
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Notification.COLOR_DEFAULT
        adminChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            adminChannel.enableVibration(true)

        notificationManager.createNotificationChannel(adminChannel)
    }
}