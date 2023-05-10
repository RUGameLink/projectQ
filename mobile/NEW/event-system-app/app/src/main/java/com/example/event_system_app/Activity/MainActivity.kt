package com.example.event_system_app.Activity


import android.content.Context
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
import com.example.event_system_app.Fragment.*
import com.example.event_system_app.Helper.MyContextWrapper
import com.example.event_system_app.Helper.SharedPrefs
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var menu: BottomNavigationView
    private lateinit var frameLayout: FrameLayout
    private lateinit var profileFragment: ProfileFragment
    private lateinit var eventsFragment: EventsFragment
    private lateinit var eventsResponsibleFragment: EventsResponsibleFragment
    private lateinit var createAnEventFragment: CreateAnEventFragment
    private lateinit var myEventsFragment: MyEventsFragment
    private lateinit var profileCleanFragment: ProfileCleanFragment
    private lateinit var presenceFragment: PresenceFragment

    private lateinit var myPreference: SharedPrefs
    private val menuToChoose: Int = R.menu.bottom_navigation_menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.events_text)
        setMenu()
        menu.setSelectedItemId(R.id.events_item);
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
                R.id.create_item -> {
                    title = getString(R.string.my_events_text)
                    createAnEventFragment = CreateAnEventFragment()
                    replaceFragment(createAnEventFragment)
                    true
                }
                R.id.event_responsible_item -> {
                    title = getString(R.string.events_text)
                    eventsResponsibleFragment = EventsResponsibleFragment()
                    replaceFragment(eventsResponsibleFragment)
                    true
                }
                R.id.presence_item -> {
                    title = getString(R.string.presence_confirmation_text)
                    presenceFragment = PresenceFragment()
                    replaceFragment(presenceFragment)
                    true
                }
                R.id.profile_item -> {
                    title = getString(R.string.profile_text)
                    var resp = myPreference.getLoginCount()
                    when(resp){
                        1 -> {
                            profileFragment = ProfileFragment()
                            replaceFragment(profileFragment)
                        }
                        2 -> {
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

    private fun setMenu() {
        var resp = myPreference.getLoginCount()
        when(resp){
            1 -> {
                profileFragment = ProfileFragment()
                menu.inflateMenu(R.menu.bottom_navigation_menu);
                eventsFragment = EventsFragment()
                replaceFragment(eventsFragment)
                menu.setSelectedItemId(R.id.events_item);
            }
            2 -> {
                profileFragment = ProfileFragment()
                menu.inflateMenu(R.menu.bottom_navigation_menu_responsible);
                eventsResponsibleFragment = EventsResponsibleFragment()
                replaceFragment(eventsResponsibleFragment)
                menu.setSelectedItemId(R.id.event_responsible_item);
            }
            0 -> {
                profileCleanFragment = ProfileCleanFragment()
                menu.inflateMenu(R.menu.bottom_navigation_menu);
                eventsFragment = EventsFragment()
                replaceFragment(eventsFragment)
                menu.setSelectedItemId(R.id.events_item);
            }
        }
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

    override fun attachBaseContext(newBase: Context?) {
        myPreference = SharedPrefs(newBase!!)
        val lang = myPreference.getLanguageCount()
        super.attachBaseContext(MyContextWrapper.wrap(newBase,lang))
    }
}