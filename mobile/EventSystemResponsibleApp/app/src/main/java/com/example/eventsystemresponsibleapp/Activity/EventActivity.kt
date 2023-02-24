package com.example.eventsystemresponsibleapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.eventsystemresponsibleapp.Model.Event
import com.example.eventsystemresponsibleapp.R
import com.google.android.material.appbar.MaterialToolbar

class EventActivity: AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var eventImage: ImageView
    private lateinit var headerText: TextView
    private lateinit var tagListText: TextView
    private lateinit var eventDateText: TextView
    private lateinit var bodyEventText: TextView
    private lateinit var eventLocationText: TextView
    private lateinit var dateOfEndText: TextView
    private lateinit var human_count_text: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.event_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)
        val event = intent.getSerializableExtra("event") as Event
        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }
        setContent(event)
    }

    //Заполнение компонентов информацией
    private fun setContent(event: Event) {
        Glide.with(this)
            .load(event.imgUrl)
            .placeholder(R.drawable.icon_events)
            .into(eventImage)

        headerText.text = event.title
        tagListText.text = event.tags
        eventDateText.text = event.date
        bodyEventText.text = event.description
        eventLocationText.text = event.location
        human_count_text.text = event.humanCount.toString()
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
        eventImage = findViewById(R.id.eventImage)
        headerText= findViewById(R.id.headerText)
        tagListText= findViewById(R.id.tagListText)
        eventDateText= findViewById(R.id.eventDateText)
        bodyEventText= findViewById(R.id.bodyEventText)
        eventLocationText= findViewById(R.id.eventLocationText)
        human_count_text= findViewById(R.id.human_count_text)
        dateOfEndText = findViewById(R.id.dateOfEndText)

    }
}