package com.example.event_system_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var menu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setSupportActionBar(toolbar)
        title = "Test"
        toolbar.isTitleCentered = true

        menu.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.my_events_item -> {
                    title = getString(R.string.my_events_text)
                    true
                }
                R.id.events_item -> {
                    title = getString(R.string.events_text)
                    true
                }
                R.id.profile_item -> {
                    title = getString(R.string.profile_text)
                    true
                }
                else -> false
            }
        }
    }

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        menu = findViewById(R.id.bottom_navigation)
    }
}