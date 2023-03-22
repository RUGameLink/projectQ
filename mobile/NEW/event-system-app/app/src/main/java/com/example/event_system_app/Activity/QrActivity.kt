package com.example.event_system_app.Activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar

class QrActivity: AppCompatActivity()  {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var qrFullscreen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_fullcsreen)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)
        val qrImage = intent.getStringExtra("qr")

        toolbar.setNavigationOnClickListener {
            super.onBackPressed();
            true
        }

        Glide.with(this)
            .load(qrImage)
            .placeholder(R.drawable.icon_events)
            .into(qrFullscreen)
    }

    //Инициализация компонентов
    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        qrFullscreen = findViewById(R.id.qrFullscreen)
    }
}