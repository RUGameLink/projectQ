package com.example.event_system_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.materialswitch.MaterialSwitch

@Suppress("DEPRECATION")
class SettingsActivity: AppCompatActivity()  {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var switch: MaterialSwitch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.settings_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_icon)
        toolbar.navigationIcon = getDrawable(R.drawable.back_icon)

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }

        switch.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        switch.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                try {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    setPref(AppCompatDelegate.MODE_NIGHT_YES)
                }
                catch (ex: Exception){
                    println(ex)
                }

            }else{
                try{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    setPref(AppCompatDelegate.MODE_NIGHT_NO)
                }
                catch (ex: Exception){
                    println(ex)
                }
            }
        }
    }
    private fun setPref(delegate: Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putInt("Theme", delegate).apply()
    }

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        switch = findViewById(R.id.theme_switch)
    }
}