package com.example.event_system_app.Helper

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class SharedPrefsTest{
    private lateinit var sharedPrefs: SharedPrefs
    val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun russian_language(){
        sharedPrefs = SharedPrefs(context)
        sharedPrefs.setLanguageCount("ru")
        var result = sharedPrefs.getLanguageCount()
        Truth.assertThat(result).isEqualTo("ru")
    }

    @Test
    fun english_language(){
        sharedPrefs = SharedPrefs(context)
        sharedPrefs.setLanguageCount("en")
        var result = sharedPrefs.getLanguageCount()
        Truth.assertThat(result).isEqualTo("en")
    }

    @Test
    fun not_login(){
        sharedPrefs = SharedPrefs(context)
        sharedPrefs.setLoginCount(0)
        var result = sharedPrefs.getLoginCount()
        Truth.assertThat(result).isEqualTo(0)
    }

    @Test
    fun stud_login(){
        sharedPrefs = SharedPrefs(context)
        sharedPrefs.setLoginCount(1)
        var result = sharedPrefs.getLoginCount()
        Truth.assertThat(result).isEqualTo(1)
    }

    @Test
    fun resp_login(){
        sharedPrefs = SharedPrefs(context)
        sharedPrefs.setLoginCount(2)
        var result = sharedPrefs.getLoginCount()
        Truth.assertThat(result).isEqualTo(2)
    }

    @Test
    fun light_theme(){
        sharedPrefs = SharedPrefs(context)
        sharedPrefs.setThemeCount(1)
        var result = sharedPrefs.getThemeCount()
        Truth.assertThat(result).isEqualTo(1)
    }

    @Test
    fun dark_theme(){
        sharedPrefs = SharedPrefs(context)
        sharedPrefs.setThemeCount(2)
        var result = sharedPrefs.getThemeCount()
        Truth.assertThat(result).isEqualTo(2)
    }
}