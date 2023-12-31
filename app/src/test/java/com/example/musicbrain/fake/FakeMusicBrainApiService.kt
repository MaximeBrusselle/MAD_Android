package com.example.musicbrain.fake

import com.example.musicbrain.network.ApiArtist
import com.example.musicbrain.network.ApiInstrument
import com.example.musicbrain.network.ArtistsResponse
import com.example.musicbrain.network.InstrumentsResponse
import com.example.musicbrain.network.MusicBrainApiService

class FakeMusicBrainApiService : MusicBrainApiService {

    override suspend fun searchArtist(query: String): ArtistsResponse {
        return ArtistsResponse(
          created = "2021-03-28T20:00:00.000Z",
            count = 2,
            offset = 0,
            artists = FakeDataSource.apiArtists.filter { it.name.contains(query, ignoreCase = true) }
        )
    }

    override suspend fun getArtist(id: String): ApiArtist {
        return FakeDataSource.apiArtists.find { it.id == id }!!
    }

    override suspend fun searchInstrument(query: String): InstrumentsResponse {
        return InstrumentsResponse(
            created = "2021-03-28T20:00:00.000Z",
            count = 2,
            offset = 0,
            instruments = FakeDataSource.apiInstruments.filter { it.name.contains(query, ignoreCase = true) }
        )
    }

    override suspend fun getInstrument(id: String): ApiInstrument {
        return FakeDataSource.apiInstruments.find { it.id == id }!!
    }
}
