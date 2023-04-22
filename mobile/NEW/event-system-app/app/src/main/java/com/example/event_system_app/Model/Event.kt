package com.example.event_system_app.Model

import android.icu.text.CaseMap.Title
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Event(
    val id: Long,
    val title: String,
    val description: String,
    val imgUrl: Array<String>,
    val tags: String,
    val date: String,
    val location: String,
    val humanCount: Int,
    val regStart: String,
    val regEnd: String,
    val organizers: String
) : Serializable