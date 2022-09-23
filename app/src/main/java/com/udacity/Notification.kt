package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object Notification {

    val id = "loadapp"
    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = id
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)
            channel.enableVibration(true)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
    }

    fun NotificationManager.createNotification(
        context: Context, status: String, message: String
    ) {
        val contentIntent = Intent(context, DetailActivity::class.java)
        contentIntent.apply {
            putExtra("fileName", message)
            putExtra("status", status)
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val action =
            NotificationCompat.Action.Builder(0, "Check the status", contentPendingIntent).build()

        val builder = NotificationCompat.Builder(
            context,
            id
        )
            .setSmallIcon(R.drawable.ic_baseline_arrow_downward_24)
            .setContentTitle(
                context
                    .getString(R.string.notification_title)
            )
            .setContentText(message)
            .setContentIntent(contentPendingIntent)
            .addAction(action)
            .setAutoCancel(true)
        notify(0, builder.build())

    }

}