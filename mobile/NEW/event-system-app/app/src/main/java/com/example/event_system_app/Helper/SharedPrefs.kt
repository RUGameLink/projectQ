package com.example.event_system_app.Helper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


private val PREFERENCE_NAME = "SharesPreference"
private val PREFERENCE_LANGUAGE = "Language"
private val PREFERENCE_LOGIN = "Login"
private val PREFERENCE_THEME = "Theme"

class SharedPrefs(context: Context){
    val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getLanguageCount(): String{
        return preferences.getString(PREFERENCE_LANGUAGE, "ru")!!
    }

    fun setLanguageCount(language: String) {
        val editor = preferences.edit()
        editor.putString(PREFERENCE_LANGUAGE, language)
        editor.apply()
    }

    fun setLoginCount(login: Int){
        val editor = preferences.edit()
        editor.putInt(PREFERENCE_LOGIN, login)
        editor.apply()
    }

    fun getLoginCount(): Int{
        return preferences.getInt(PREFERENCE_LOGIN, 0)!!
    }

    fun setThemeCount(theme: Int){
        val editor = preferences.edit()
        editor.putInt(PREFERENCE_THEME, theme)
        editor.apply()
    }

    fun getThemeCount(): Int{
        return preferences.getInt(PREFERENCE_THEME, 0)!!
    }

}