package com.example.event_system_app.Activity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton

class NetworkErrorActivity : AppCompatActivity() {
    private lateinit var refreshButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_error)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        init()

        refreshButton.setOnClickListener {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(){
        refreshButton = findViewById(R.id.refreshButton)
    }
}