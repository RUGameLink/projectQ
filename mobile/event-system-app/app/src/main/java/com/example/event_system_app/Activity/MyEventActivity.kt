package com.example.event_system_app.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.event_system_app.Model.MyEvent
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyEventActivity: AppCompatActivity()  {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var headerMyText: TextView
    private lateinit var myTagListText: TextView
    private lateinit var myEventDateText: TextView
    private lateinit var qrImg: ImageView
    private lateinit var downloadButton: FloatingActionButton
    private lateinit var shareButton: FloatingActionButton
    private lateinit var myEventLocationText: TextView
    private lateinit var eventPageButton: MaterialButton
    private lateinit var cancelButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_event)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.event_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_icon)
        toolbar.navigationIcon = getDrawable(R.drawable.back_icon)
        val event = intent.getSerializableExtra("myEvent") as MyEvent

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }
        setContent(event)

        eventPageButton.setOnClickListener {
            Toast.makeText(this, "Я открою страницу мероприятия", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            Toast.makeText(this, "Я отменю регистрацию", Toast.LENGTH_SHORT).show()
        }

        downloadButton.setOnClickListener {
            Toast.makeText(this, "Я скачаю файл", Toast.LENGTH_SHORT).show()
        }

        shareButton.setOnClickListener {
            Toast.makeText(this, "Я расшарю файл", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setContent(event: MyEvent) {
        Glide.with(this)
            .load(event.qrImg)
            .placeholder(R.drawable.events_icon)
            .into(qrImg)

        headerMyText.text = event.title
        myTagListText.text = event.tags
        myEventDateText.text = event.date
        myEventLocationText.text = event.location
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

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

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        headerMyText = findViewById(R.id.headerMyText)
        myTagListText = findViewById(R.id.myTagListText)
        myEventDateText = findViewById(R.id.myEventDateText)
        qrImg = findViewById(R.id.qrImg)
        downloadButton = findViewById(R.id.downloadButton)
        shareButton = findViewById(R.id.shareButton)
        myEventLocationText = findViewById(R.id.myEventLocationText)
        eventPageButton = findViewById(R.id.event_page_button)
        cancelButton = findViewById(R.id.cancel_button)
    }
}