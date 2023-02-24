package com.example.eventsystemresponsibleapp.Model

import java.io.Serializable

data class MyEvent(
    val id: Long,
    val eventId: Long,
    val title: String,
    val qrImg: String,
    val tags: String,
    val date: String,
    val location: String,
): Serializable
