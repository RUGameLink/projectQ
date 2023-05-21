package com.example.event_system_app.Activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton

class NetworkErrorActivity : AppCompatActivity() {
    private lateinit var refreshButton: MaterialButton
    private lateinit var serverHelper: ServerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_error)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        serverHelper = ServerHelper(this)
        init()

        refreshButton.setOnClickListener {
            if(serverHelper.isOnline(this)){
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun init(){
        refreshButton = findViewById(R.id.refreshButton)
    }

    override fun onBackPressed() {}
}