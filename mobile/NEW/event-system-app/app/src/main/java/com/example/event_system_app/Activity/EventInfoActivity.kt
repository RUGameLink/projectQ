package com.example.event_system_app.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import java.security.AccessController.getContext


class EventInfoActivity : AppCompatActivity() {
    private lateinit var carouselView: CarouselView
    private lateinit var eventTitleInfoText: TextView
    private lateinit var eventTagsInfoText: TextView
    private lateinit var dateInfoText: TextView
    private lateinit var descInfoText: TextView
    private lateinit var locationInfoText: TextView
    private lateinit var regFromInfoText: TextView
    private lateinit var regToInfoText: TextView
    private lateinit var orgInfoText: TextView
    private lateinit var statsButton: MaterialButton

    private lateinit var event: Event


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        init()
        event = intent.getSerializableExtra("event") as Event
        setImages()
        setInfo()

        statsButton.setOnClickListener {
            val i = Intent(this, EventStatsActivity::class.java)
            i.putExtra("eventName", event.title)
            i.putExtra("eventId", event.id.toString())
            startActivity(i)
        }
    }

    private fun setImages() {
        carouselView = findViewById(R.id.infoImagesVies);
        carouselView.setPageCount(event.imgUrl!!.size);

        carouselView.setImageListener(imageListener);
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            Glide.with(this@EventInfoActivity)
                .load(event.imgUrl!!.get(position))
                .placeholder(R.drawable.icon_events)
                .into(imageView)
        }
    }

    private fun setInfo() {
        eventTitleInfoText.text = event.title
        eventTagsInfoText.text = event.tags
        dateInfoText.text = event.date
        descInfoText.text = event.description
        locationInfoText.text = event.location
        regFromInfoText.text = event.regStart
        regToInfoText.text = event.regEnd
        orgInfoText.text = event.organizers
    }

    private fun init(){
        eventTitleInfoText = findViewById(R.id.eventTitleInfoText)
        eventTagsInfoText = findViewById(R.id.eventTagsInfoText)
        dateInfoText = findViewById(R.id.dateInfoText)
        descInfoText = findViewById(R.id.descInfoText)
        locationInfoText = findViewById(R.id.locationInfoText)
        regFromInfoText = findViewById(R.id.regFromInfoText)
        regToInfoText = findViewById(R.id.regToInfoText)
        orgInfoText = findViewById(R.id.orgInfoText)
        statsButton = findViewById(R.id.statsButton)
    }
}