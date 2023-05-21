package com.example.event_system_app.Activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.event_system_app.test.R
import org.junit.Assert.*
import org.junit.Test

class MainActivityTest{
    @Test
    fun start_activity(){
        val activity = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(com.example.event_system_app.R.id.main)).check(matches(isDisplayed()))
    }
}