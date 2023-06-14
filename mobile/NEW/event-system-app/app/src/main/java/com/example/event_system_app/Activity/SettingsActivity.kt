package com.example.event_system_app.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.Helper.SharedPrefs
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch


@Suppress("DEPRECATION")
class SettingsActivity: AppCompatActivity()  {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var switch: MaterialSwitch
    private lateinit var translateButton: MaterialButton

    private lateinit var appPrefs: SharedPrefs
    private lateinit var serverHelper: ServerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
        serverHelper = ServerHelper(this)
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.settings_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)

        checkConnection()

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }

        appPrefs = SharedPrefs(this)

        switch.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        switch.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                try {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    appPrefs.setThemeCount(AppCompatDelegate.MODE_NIGHT_YES)
                }
                catch (ex: Exception){
                    println(ex)
                    restartActivity(this)
                }

            }else{
                try{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    appPrefs.setThemeCount(AppCompatDelegate.MODE_NIGHT_NO)
                }
                catch (ex: Exception){
                    println(ex)
                    restartActivity(this)
                }
            }
        }

        translateButton.setOnClickListener(translateListener)
        appPrefs.setLoginCount(1)
    }

    private fun checkConnection() {
        if(!serverHelper.isOnline(this)){
            val i = Intent(this, NetworkErrorActivity::class.java)
            startActivity(i)
        }
    }

    //Перезагрузка графики активити
    fun restartActivity(activity: Activity) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    //Слушатель кнопки перевода
    private val translateListener: View.OnClickListener = View.OnClickListener {
        showTranslateDialog()
    }

    //Запуск диалога смены языка
    private fun showTranslateDialog() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_language, null)
        builder.setView(dialogView)

        val russianRadioButton = dialogView.findViewById<RadioButton>(R.id.rus_rbtn)
        val englishRadioButton = dialogView.findViewById<RadioButton>(R.id.eng_rbtn)

        when(appPrefs.getLanguageCount()){
            "en" -> {
                englishRadioButton.isChecked = true
                russianRadioButton.isChecked = false
            }
            "ru" -> {
                englishRadioButton.isChecked = false
                russianRadioButton.isChecked = true
            }

        }

        russianRadioButton.setOnClickListener {
            englishRadioButton.isChecked = false
            russianRadioButton.isChecked = true
            changeLanguage("ru")
        }

        englishRadioButton.setOnClickListener {
            russianRadioButton.isChecked = false
            englishRadioButton.isChecked = true
            changeLanguage("en")
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun changeLanguage(language: String){
        appPrefs.setLanguageCount(language)
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    //Инициализация компонентов
    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        switch = findViewById(R.id.theme_switch)
        translateButton = findViewById(R.id.translateButton)
    }
    /////////////////////////
}