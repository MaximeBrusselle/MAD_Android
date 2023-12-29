package com.example.musicbrain.data

import com.example.musicbrain.model.Artist
import kotlin.random.Random

object ArtistSampler {
    val sampleArtists = mutableListOf(
        Artist(
            id = "bc810ce5-7c02-4856-a704-ea0126b3ccfa",
            type = "Person",
            score = 100,
            name = "DEEZL",
            gender = "male",
//            area = Area(
//                id = "3f179da4-83c6-4a28-a627-e46b4a8ff1ed",
//                type = "City",
//                name = "Sydney",
//            ),
            disambiguation = "Hardstyle producer",
//            lifeSpan = null
        ),
        Artist(
            id = "cc39b1a2-349b-4c99-8822-579df96f9fab",
            type = "Person",
            score = 100,
            name = "Anderex",
            gender = "male",
//            area = Area(
//                id = "106e0bec-b638-3b37-b731-f53d507dc00e",
//                type = "Country",
//                name = "Australia",
//            ),
            disambiguation = "",
//            lifeSpan = null
        ),
        Artist(
            id = "50b19a32-a385-4d50-b7b5-567c41a2f2ca",
            type = "Person",
            score = 100,
            name = "Exproz",
            gender = "male",
//            area = Area(
//                id = "08310658-51eb-3801-80de-5a0739207115",
//                type = "Country",
//                name = "France",
//            ),
            disambiguation = "French Hardstyle DJ & Producer",
//            lifeSpan = null
        )

    )

    val getAll: () -> MutableList<Artist> = {
        val list = mutableListOf<Artist>()
        val sampleArtist1 = Artist(
            id = "1b05edc2-6fb2-4242-a8f4-d6f9795cbc7a",
            type = "Person",
            score = 100,
            name = "So Juice",
        )
        val sampleArtist2 = Artist(
            id = "e80a4749-9c71-4bc4-a685-1ccef33e3265",
            type = "Person",
            score = 100,
            name = "Fraw",
        )
        for (item in sampleArtists) {
            list.add(if (Random.nextInt(0, 2) == 0) { sampleArtist1 } else sampleArtist2)
        }
        list
    }
}