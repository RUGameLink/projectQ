package com.example.eventsystemresponsibleapp.Model

import java.io.Serializable

data class Event(
    val id: Long,
    val title: String,
    val description: String,
    val imgUrl: String,
    val tags: String,
    val date: String,
    val location: String,
    val humanCount: Int
) : Serializable