package com.example.event_system_app.Activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.Helper.SharedPrefs
import com.example.event_system_app.Model.Event
import com.example.event_system_app.Model.User
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
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
    private lateinit var progressBar: ProgressBar
    private lateinit var eventLayout: RelativeLayout
    private lateinit var regButton: MaterialButton

    private lateinit var event: Event
    private lateinit var serverHelper: ServerHelper
    private lateinit var myPreference: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        init()
        myPreference = SharedPrefs(this)
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.event_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)
        serverHelper = ServerHelper(this)
        checkConnection()
        val user = User(1, "efef", "efe", "efef", "efef")
        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }

        val eventId = intent.getStringExtra("eventId")


        serverHelper.getEventInfo(eventId!!)
        event = serverHelper.getEvent()
        val resp = myPreference.getLoginCount()
        if(resp == 2)
            regButton.visibility = View.INVISIBLE

        regButton.setOnClickListener {
            var resp = myPreference.getLoginCount()
            when(resp){
                0 -> {
                    Toast.makeText(this, getString(R.string.clean_profile_text), Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    serverHelper.regForEvent(user.id, event.id)
                }
            }
        }

        checkList()

      //  setContent()
    }

    private fun checkConnection() {
        if(!serverHelper.isOnline(this)){
            val i = Intent(this, NetworkErrorActivity::class.java)
            startActivity(i)
        }
    }

    private fun checkList() {
        println("check List - is ${event}")
        if(event.id == null){
            Handler().postDelayed({
                event = serverHelper.getEvent()
                checkList()
            }, 2000)
        }
        else{
            setContent()
        }
    }

    //Заполнение компонентов информацией
    private fun setContent() {
        headerText.text = event.title
        tagListText.text = event.tags
        eventDateText.text = event.date
        bodyEventText.text = event.description
        eventLocationText.text = event.location
        human_count_text.text = event.humanCount.toString()
        setImages()
        eventLayout.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    private fun setImages() {
        eventImage = findViewById(R.id.eventImage);
        eventImage.setImageListener(imageListener);
        eventImage.setPageCount(event.imgUrl!!.size);
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            Glide.with(this@EventActivity)
                .load(event.imgUrl!!.get(position))
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
        progressBar = findViewById(R.id.progressBar)
        eventLayout = findViewById(R.id.eventsLayout)
        regButton = findViewById(R.id.go_to_event_button)
    }

    ////////////////////////////////////////////////////////////////////////////////

}