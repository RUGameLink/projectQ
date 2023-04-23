package com.example.event_system_app.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class EventActivity: AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var eventImage: CarouselView
    private lateinit var headerText: TextView
    private lateinit var tagListText: TextView
    private lateinit var eventDateText: TextView
    private lateinit var bodyEventText: TextView
    private lateinit var eventLocationText: TextView
    private lateinit var human_count_text: TextView

    private lateinit var event: Event


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.event_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)
        event = intent.getSerializableExtra("event") as Event
        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }
        setContent()
    }

    //Заполнение компонентов информацией
    private fun setContent() {
        setImages()

        headerText.text = event.title
        tagListText.text = event.tags
        eventDateText.text = event.date
        bodyEventText.text = event.description
        eventLocationText.text = event.location
        human_count_text.text = event.humanCount.toString()
    }

    private fun setImages() {
        eventImage = findViewById(R.id.eventImage);
        eventImage.setPageCount(event.imgUrl.size);

        eventImage.setImageListener(imageListener);
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            Glide.with(this@EventActivity)
                .load(event.imgUrl.get(position))
                .placeholder(R.drawable.icon_events)
                .into(imageView)
        }
    }

    //Создание меню настроек
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }
    //Слушатель элементов меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_item -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Инициализация компонентов
    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        headerText= findViewById(R.id.headerText)
        tagListText= findViewById(R.id.tagListText)
        eventDateText= findViewById(R.id.eventDateText)
        bodyEventText= findViewById(R.id.bodyEventText)
        eventLocationText= findViewById(R.id.eventLocationText)
        human_count_text= findViewById(R.id.human_count_text)

    }
}