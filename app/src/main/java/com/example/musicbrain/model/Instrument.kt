package com.example.musicbrain.model

/**
 * Data class representing an instrument.
 *
 * @property id The unique identifier of the instrument.
 * @property name The name of the instrument (default is an empty string).
 * @property type The type of the instrument (default is "Unknown").
 * @property description The description of the instrument (default is "No description provided").
 * @property score The score associated with the instrument (default is 0).
 */
data class Instrument(
    val id: String,
    val name: String = "",
    val type: String = "Unknown",
    val description: String = "No description provided",
    val score: Int = 0,
)
