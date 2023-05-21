package com.example.event_system_app.Model

import java.io.Serializable

data class User(
    val id: Long,
    val userName: String,
    val userImageURL: String,
    val userGroup: String,
    val role: String
): Serializable
