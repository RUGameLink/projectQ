package com.example.event_system_app.Model

import android.icu.text.CaseMap.Title

data class Event(
    val id: Long,
    val title: String,
    val description: String,
    val imgUrl: String,
    val tags: String,
    val date: String,
    val location: String,
    val count: Int
)
