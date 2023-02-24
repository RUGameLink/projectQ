package com.example.eventsystemresponsibleapp.Activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.eventsystemresponsibleapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch

@Suppress("DEPRECATION")
class SettingsActivity: AppCompatActivity()  {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var switch: MaterialSwitch
    private lateinit var translateButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.settings_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)

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
                    restartActivity(this)
                }

            }else{
                try{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    setPref(AppCompatDelegate.MODE_NIGHT_NO)
                }
                catch (ex: Exception){
                    println(ex)
                    restartActivity(this)
                }
            }
        }

        translateButton.setOnClickListener(translateListener)
    }

    //Перезагрузка графики активити
    fun restartActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate()
        } else {
            activity.finish()
            activity.startActivity(activity.intent)
        }
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
        russianRadioButton.isChecked = true
        val englishRadioButton = dialogView.findViewById<RadioButton>(R.id.eng_rbtn)

        russianRadioButton.setOnClickListener {
            englishRadioButton.isChecked = false
            russianRadioButton.isChecked = true
            Toast.makeText(this, "Будет русский", Toast.LENGTH_SHORT).show()
        }

        englishRadioButton.setOnClickListener {
            russianRadioButton.isChecked = false
            englishRadioButton.isChecked = true
            Toast.makeText(this, "Будет английский", Toast.LENGTH_SHORT).show()
        }

        val dialog = builder.create()
        dialog.show()
    }

    //Чтение префа темы
    private fun setPref(delegate: Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putInt("Theme", delegate).apply()
    }

    //Инициализация компонентов
    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        switch = findViewById(R.id.theme_switch)
        translateButton = findViewById(R.id.translateButton)
    }
}