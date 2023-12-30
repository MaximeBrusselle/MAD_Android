package com.example.musicbrain.model

data class Artist(
    val id: String,
    val type: String = "",
    val score: Int = 0,
    val name: String = "",
    val gender: String = "Unknown",
    val disambiguation: String = "Unknown",
)