package com.example.eventsystemresponsibleapp.Model

import java.io.Serializable

data class User(
    val id: Long,
    val userName: String,
    val userImageURL: String,
    val userGroup: String,
    val uid: String
): Serializable
