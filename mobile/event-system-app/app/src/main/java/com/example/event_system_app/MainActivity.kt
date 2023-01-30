package com.example.event_system_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.event_system_app.Fragments.EventsFragment
import com.example.event_system_app.Fragments.MyEventsFragment
import com.example.event_system_app.Fragments.ProfileFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var menu: BottomNavigationView
    private lateinit var frameLayout: FrameLayout
    private lateinit var profileFragment: ProfileFragment
    private lateinit var eventsFragment: EventsFragment
    private lateinit var myEventsFragment: MyEventsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.events_text)
        menu.setSelectedItemId(R.id.events_item);
        eventsFragment = EventsFragment()
        replaceFragment(eventsFragment)

        menu.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.my_events_item -> {
                    title = getString(R.string.my_events_text)
                    myEventsFragment = MyEventsFragment()
                    replaceFragment(myEventsFragment)
                    true
                }
                R.id.events_item -> {
                    title = getString(R.string.events_text)
                    eventsFragment = EventsFragment()
                    replaceFragment(eventsFragment)
                    true
                }
                R.id.profile_item -> {
                    title = getString(R.string.profile_text)
                    profileFragment = ProfileFragment()
                    replaceFragment(profileFragment)
                    true
                }
                else -> false
            }
        }

        getPref()
    }

    private fun getPref() {
        var style = 0
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.apply {
            style = getInt("Theme", 0)

            if (style != 0){
                setStyle(style)
            }
        }
    }

    private fun setStyle(themeStyle: Int) {
        AppCompatDelegate.setDefaultNightMode(themeStyle);
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

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        menu = findViewById(R.id.bottom_navigation)
        frameLayout = findViewById(R.id.frame)
    }
}