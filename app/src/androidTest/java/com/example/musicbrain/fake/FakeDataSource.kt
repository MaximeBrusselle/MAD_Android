package com.example.musicbrain.fake

import com.example.musicbrain.network.ApiArtist
import com.example.musicbrain.network.ApiInstrument

object FakeDataSource {
    private const val artistIdOne = "bc810ce5-7c02-4856-a704-ea0126b3ccfa"
    private const val artistIdTwo = "1b05edc2-6fb2-4242-a8f4-d6f9795cbc7a"

    private const val artistTypeOne = "Person"
    private const val artistTypeTwo = "Person"

    private const val artistScoreOne = 100
    private const val artistScoreTwo = 100

    private const val artistNameOne = "DEEZL"
    private const val artistNameTwo = "So Juice"

    private const val artistgenderOne = "male"
    private const val artistgenderTwo = "male"

    private const val artistDisambiguationOne = "Rawstyle producer"
    private const val artistDisambiguationTwo = "Rawstyle producer"

    val apiArtists: List<ApiArtist> = listOf(
        ApiArtist(artistIdOne, artistTypeOne, artistScoreOne, artistNameOne, artistgenderOne, artistDisambiguationOne),
        ApiArtist(artistIdTwo, artistTypeTwo, artistScoreTwo, artistNameTwo, artistgenderTwo, artistDisambiguationTwo),
    )

    private const val instrumentIdOne = "b3eac5f9-7859-4416-ac39-7154e2e8d348"
    private const val instrumentIdTwo = "1c8f9780-2f16-4891-b66d-bb7aa0820dbd"

    private const val instrumentTypeOne = "String instrument"
    private const val instrumentTypeTwo = "Wind instrument"

    private const val instrumentScoreOne = 100
    private const val instrumentScoreTwo = 100

    private const val instrumentNameOne = "piano"
    private const val instrumentNameTwo = "trumpet"

    private const val instrumentDescriptionOne = "piano desription"
    private const val instrumentDescriptionTwo = "trumpet description"

    val apiInstruments: List<ApiInstrument> = listOf(
        ApiInstrument(instrumentIdOne, instrumentTypeOne, instrumentScoreOne, instrumentNameOne, instrumentDescriptionOne),
        ApiInstrument(instrumentIdTwo, instrumentTypeTwo, instrumentScoreTwo, instrumentNameTwo, instrumentDescriptionTwo),
    )

    
}
