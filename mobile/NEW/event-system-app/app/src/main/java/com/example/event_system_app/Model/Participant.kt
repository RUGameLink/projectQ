package com.example.event_system_app.Model

data class Participant(
    val eventId: Long,
    val userName: String,
    val studentGroup: String,
    val presence: String,
    val role: String,
    val presenceNoted: String
)
