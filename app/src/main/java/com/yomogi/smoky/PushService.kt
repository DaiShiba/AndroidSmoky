package com.yomogi.smoky

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService : FirebaseMessagingService() {

    val LOG_TAG = PushService::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        //FCMメッセージの処理
        super.onMessageReceived(remoteMessage)

        Log.d(LOG_TAG, "Title;${remoteMessage?.notification?.title}")
        Log.d(LOG_TAG,"body;${remoteMessage?.notification?.body}")

        //Notification
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(this, "fcm_default_channel")
            .setSmallIcon(R.drawable.notification_template_icon_bg)
            .setContentTitle(remoteMessage?.notification?.title)
            .setContentText(remoteMessage?.notification?.body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        //Oreo向けにチャンネル設定
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("fcm_default_channel","name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        //通知
        notificationManager.notify(0, notificationBuilder.build())
    }
}