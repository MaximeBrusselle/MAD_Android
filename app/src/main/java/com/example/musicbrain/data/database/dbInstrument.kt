package com.example.musicbrain.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicbrain.model.Instrument

/**
 * Represents an instrument entity in the local Room database.
 *
 * @property id The unique identifier of the instrument.
 * @property name The name of the instrument.
 * @property type The type of the instrument (default is "Unknown").
 * @property description A description of the instrument (default is "Unknown").
 * @property score The score associated with the instrument.
 */
@Entity(tableName = "instruments")
data class DbInstrument(
    @PrimaryKey
    val id: String,
    val name: String = "",
    val type: String = "Unknown",
    val description: String = "No description provided",
    val score: Int = 0,
)

/**
 * Converts a [DbInstrument] object to an [Instrument] domain model.
 *
 * @return An instance of [Instrument] representing the same data as the [DbInstrument].
 */
fun DbInstrument.asDomainInstrument(): Instrument {
    return Instrument(
        this.id,
        this.name,
        this.type,
        this.description,
        this.score,
    )
}

/**
 * Converts an [Instrument] domain model to a [DbInstrument] object.
 *
 * @return An instance of [DbInstrument] representing the same data as the [Instrument].
 */
fun Instrument.asDbInstrument(): DbInstrument {
    return DbInstrument(
        id = this.id,
        name = this.name,
        type = this.type,
        description = this.description,
        score = this.score,
    )
}

/**
 * Converts a list of [DbInstrument] objects to a list of [Instrument] domain models.
 *
 * @return A list of [Instrument] objects representing the same data as the list of [DbInstrument].
 */
fun List<DbInstrument>.asDomainInstruments(): List<Instrument> {
    return this.map {
        Instrument(
            it.id,
            it.name,
            it.type,
            it.description,
            it.score,
        )
    }
}
