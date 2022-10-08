package com.udacity.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.R

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

//notification needs notification builder with title , content, icon and channel

//extension function created from NotificationManager class so it can be used anywhere in the app
fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context,
    status: Int,
    selectedURL: String
) {

    val intent = Intent(applicationContext, DetailActivity::class.java)
    intent.putExtra("Repo",selectedURL)
    intent.putExtra("Status",status)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    val pendingIntent = PendingIntent.getActivity(applicationContext,1,intent, PendingIntent.FLAG_ONE_SHOT)


// use notification compat builder to support older versions
    val builder = NotificationCompat.Builder(
        applicationContext,
        "StatusDownloadID")
    //set content title and icon
        .setSmallIcon(R.drawable.abc_vector_test)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .addAction(
            R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.notification_button),
            pendingIntent
        )
        .setAutoCancel(true)


    //call notify , using an extension function no need to add NotifiactionManager and call build
    //on our notification builder variable

    notify(NOTIFICATION_ID,builder.build())

}

