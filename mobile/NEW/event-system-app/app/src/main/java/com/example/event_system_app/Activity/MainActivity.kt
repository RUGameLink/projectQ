package com.example.event_system_app.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.event_system_app.Fragment.EventsFragment
import com.example.event_system_app.Fragment.MyEventsFragment
import com.example.event_system_app.Fragment.ProfileCleanFragment
import com.example.event_system_app.Fragment.ProfileFragment
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var menu: BottomNavigationView
    private lateinit var frameLayout: FrameLayout
    private lateinit var profileFragment: ProfileFragment
    private lateinit var eventsFragment: EventsFragment
    private lateinit var myEventsFragment: MyEventsFragment
    private lateinit var profileCleanFragment: ProfileCleanFragment

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
                    var resp = checkUser()
                    when(resp){
                        1 -> {
                            profileFragment = ProfileFragment()
                            replaceFragment(profileFragment)
                        }
                        0 -> {
                            profileCleanFragment = ProfileCleanFragment()
                            replaceFragment(profileCleanFragment)
                        }
                    }

                    true
                }
                else -> false
            }
        }

        getPref()
    }

    //Проверка входа юзера
    private fun checkUser(): Int{
        var user = 0
        val pref  = PreferenceManager.getDefaultSharedPreferences(this)
        pref.apply {
            user = getInt("login", 0)
        }
        return user
    }

    //Получение информации и смена темы
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

    //Установка темы
    private fun setStyle(themeStyle: Int) {
        AppCompatDelegate.setDefaultNightMode(themeStyle);
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

    //Смена фрагмента
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }

    //Создание меню настройки
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    //Инициализация компонентов
    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        menu = findViewById(R.id.bottom_navigation)
        frameLayout = findViewById(R.id.frame)
    }
}