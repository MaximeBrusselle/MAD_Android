package com.example.musicbrain.model

data class Instrument(
    val id: String,
    val name: String = "",
    val type: String = "",
    val description: String = "",
    val score: Int = 0,
)