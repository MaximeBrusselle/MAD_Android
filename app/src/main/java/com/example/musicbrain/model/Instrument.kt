package com.example.musicbrain.model

data class Instrument(
    val id: String,
    val name: String = "",
    val type: String = "Unknown",
    val description: String = "No description provided",
    val score: Int = 0,
)