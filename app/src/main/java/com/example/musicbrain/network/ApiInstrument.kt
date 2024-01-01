package com.example.musicbrain.network

import com.example.musicbrain.model.Instrument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable data class representing an instrument retrieved from the API.
 *
 * @property id The unique identifier of the instrument.
 * @property type The type of the instrument (default is "Unknown").
 * @property score The score associated with the instrument (default is 0).
 * @property name The name of the instrument.
 * @property description The description of the instrument (default is "No description provided").
 */
@Serializable
data class ApiInstrument(
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = "Unknown",
    @SerialName("score") val score: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("description") val description: String = "No description provided",
)

/**
 * Extension function to convert a flow of lists of [ApiInstrument] to a flow of lists of [Instrument].
 */
fun Flow<List<ApiInstrument>>.asDomainObjects(): Flow<List<Instrument>> {
    return map { it.asDomainObjects() }
}

/**
 * Extension function to convert a list of [ApiInstrument] to a list of [Instrument].
 */
fun List<ApiInstrument>.asDomainObjects(): List<Instrument> {
    return map { it.asDomainObject() }
}

/**
 * Extension function to convert a flow of [ApiInstrument] to a flow of [Instrument].
 */
fun Flow<ApiInstrument>.asDomainObject(): Flow<Instrument> {
    return map { it.asDomainObject() }
}

/**
 * Extension function to convert an [ApiInstrument] to an [Instrument].
 */
fun ApiInstrument.asDomainObject(): Instrument {
    return Instrument(
        id = this.id,
        type = this.type,
        score = this.score,
        name = this.name,
        description = this.description,
    )
}
