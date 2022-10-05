package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.udacity.databinding.ActivityMainBinding
import com.udacity.util.sendNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0


    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var selectedURL: String
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))



        custom_button.setOnClickListener {

           val radioButtonId = downloadRadioGroup!!.checkedRadioButtonId
            if(radioButtonId<0){
                Toast.makeText(this,"Please Select a Repo",Toast.LENGTH_SHORT).show()
            }
            else{
                custom_button.buttonState = ButtonState.Loading
                val radioButton = findViewById<RadioButton>(radioButtonId)
                val index = downloadRadioGroup.indexOfChild(radioButton)

                if(index==0){
                    Toast.makeText(this,"Downloading Glide",Toast.LENGTH_SHORT).show()
                    selectedURL = URL_Glide
                }
               else if(index==1){
                    Toast.makeText(this,"Downloading LoadApp",Toast.LENGTH_SHORT).show()
                    selectedURL = URL_Udacity
                }
                else if(index==2){
                    Toast.makeText(this,"Downloading Retrofit",Toast.LENGTH_SHORT).show()
                    selectedURL = URL_Retrofit
                }



                download(selectedURL)
            }

//        notificationManager = ContextCompat.getSystemService(applicationContext,
//            NotificationManager::class.java)as NotificationManager
//            createChannel("Test ChannelID","Test Notification")
//            notificationManager.sendNotification("simple test notif",applicationContext)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }
//Notifications need to sent on specific channels on newer api versions
    fun createChannel(channelId: String, channelName: String) {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "A Test Notification"

           notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    private fun download(url: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.

    }

    companion object {
        private const val URL_Glide =
            "https://github.com/bumptech/glide"
        private const val URL_Udacity =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val URL_Retrofit =
            "https://github.com/square/retrofit"
        private const val CHANNEL_ID = "channelId"
    }

}
