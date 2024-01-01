package com.example.musicbrain.fake

import com.example.musicbrain.network.ApiArtist
import com.example.musicbrain.network.ApiInstrument

/**
 * A fake data source for testing purposes.
 */
object FakeDataSource {
    private const val ARTIST_ID_ONE = "bc810ce5-7c02-4856-a704-ea0126b3ccfa"
    private const val ARTIST_ID_TWO = "1b05edc2-6fb2-4242-a8f4-d6f9795cbc7a"

    private const val ARTIST_TYPE_ONE = "Person"
    private const val ARTIST_TYPE_TWO = "Person"

    private const val ARTIST_SCORE_ONE = 100
    private const val ARTIST_SCORE_TWO = 100

    private const val ARTIST_NAME_ONE = "DEEZL"
    private const val ARTIST_NAME_TWO = "So Juice"

    private const val ARTIST_GENDER_ONE = "male"
    private const val ARTIST_GENDER_TWO = "male"

    private const val ARTIST_DISAMBIGUATION_ONE = "Rawstyle producer"
    private const val ARTIST_DISAMBIGUATION_TWO = "Rawstyle producer"

    /**
     * A list of [ApiArtist] objects to be used for testing.
     */
    val apiArtists: List<ApiArtist> =
        listOf(
            ApiArtist(ARTIST_ID_ONE, ARTIST_TYPE_ONE, ARTIST_SCORE_ONE, ARTIST_NAME_ONE, ARTIST_GENDER_ONE, ARTIST_DISAMBIGUATION_ONE),
            ApiArtist(ARTIST_ID_TWO, ARTIST_TYPE_TWO, ARTIST_SCORE_TWO, ARTIST_NAME_TWO, ARTIST_GENDER_TWO, ARTIST_DISAMBIGUATION_TWO),
        )

    private const val INSTRUMENT_ID_ONE = "b3eac5f9-7859-4416-ac39-7154e2e8d348"
    private const val INSTRUMENT_ID_TWO = "1c8f9780-2f16-4891-b66d-bb7aa0820dbd"

    private const val INSTRUMENT_TYPE_ONE = "String instrument"
    private const val INSTRUMENT_TYPE_TWO = "Wind instrument"

    private const val INSTRUMENT_SCORE_ONE = 100
    private const val INSTRUMENT_SCORE_TWO = 100

    private const val INSTRUMENT_NAME_ONE = "piano"
    private const val INSTRUMENT_NAME_TWO = "trumpet"

    private const val INSTRUMENT_DESCRIPTION_ONE = "piano description"
    private const val INSTRUMENT_DESCRIPTION_TWO = "trumpet description"

    /**
     * A list of [ApiInstrument] objects to be used for testing.
     */
    val apiInstruments: List<ApiInstrument> =
        listOf(
            ApiInstrument(INSTRUMENT_ID_ONE, INSTRUMENT_TYPE_ONE, INSTRUMENT_SCORE_ONE, INSTRUMENT_NAME_ONE, INSTRUMENT_DESCRIPTION_ONE),
            ApiInstrument(INSTRUMENT_ID_TWO, INSTRUMENT_TYPE_TWO, INSTRUMENT_SCORE_TWO, INSTRUMENT_NAME_TWO, INSTRUMENT_DESCRIPTION_TWO),
        )
}
