package com.example.event_system_app.Helper

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.event_system_app.Model.Event
import com.google.common.truth.Truth.assertThat

import org.junit.Test

class ServerHelperTest {
    private lateinit var serverHelper: ServerHelper
    val context = ApplicationProvider.getApplicationContext<Context>()
    @Test
    fun internet_connection_is_true(){
        serverHelper = ServerHelper(context)
        var result = serverHelper.isOnline(context)
        assertThat(result).isTrue()
    }

    @Test
    fun internet_connection_is_false(){
        serverHelper = ServerHelper(context)
        var result = serverHelper.isOnline(context)
        assertThat(result).isFalse()
    }

}