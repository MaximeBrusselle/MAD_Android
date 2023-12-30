package com.example.musicbrain.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicbrain.model.Instrument

@Entity(tableName = "instruments")
data class dbInstrument(
    @PrimaryKey
    val id: String,
    val name: String = "",
    val type: String = "Unknown",
    val description: String = "Unknown",
    val score: Int = 0,
)

fun dbInstrument.asDomainInstrument(): Instrument {
    return Instrument(
        this.id,
        this.name,
        this.type,
        this.description,
        this.score,
    )
}

fun Instrument.asDbInstrument(): dbInstrument {
    return dbInstrument(
        id = this.id,
        name = this.name,
        type = this.type,
        description = this.description,
        score = this.score,
    )
}

fun List<dbInstrument>.asDomainInstruments(): List<Instrument> {
    val instrumentList = this.map {
        Instrument(
            it.id,
            it.name,
            it.type,
            it.description,
            it.score,
        )
    }
    return instrumentList
}