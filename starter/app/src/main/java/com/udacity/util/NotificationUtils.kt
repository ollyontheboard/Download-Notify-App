package com.udacity.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.udacity.R

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

//notification needs notification builder with title , content, icon and channel

//extension function created from NotificationManager class so it can be used anywhere in the app
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


// use notification compat builder to support older versions
    val builder = NotificationCompat.Builder(
        applicationContext,
        "Test ChannelID")
    //set content title and icon
        .setSmallIcon(R.drawable.abc_vector_test)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)

    //call notify , using an extension function no need to add NotifiactionManager and call build
    //on our notification builder variable

    notify(NOTIFICATION_ID,builder.build())
}

