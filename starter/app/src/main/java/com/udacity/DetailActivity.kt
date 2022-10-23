package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private var status = 0
    private val NOTIFICATION_ID = 0
    lateinit var statusString: String
    private lateinit var repo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(NOTIFICATION_ID)

        val extravalues = intent.extras
       repo = extravalues?.getString("Repo").toString()
        val statusInt = extravalues?.get("Status")
        if(statusInt==8){
            statusString = "Success"
        }
        else{
            statusString = "Fail"
        }
        statusValueTV.text = statusString
        repoNameTV.text = repo

       fab.setOnClickListener {
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

    }

}
