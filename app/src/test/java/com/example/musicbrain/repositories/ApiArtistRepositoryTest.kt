package com.example.musicbrain.repositories


import com.example.musicbrain.fake.FakeApiArtistRepository
import com.example.musicbrain.fake.FakeDataSource
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiArtistRepositoryTest {
    private var testId = "bc810ce5-7c02-4856-a704-ea0126b3ccfa"
    private var testSearch = "DEEZL"

    @Test
    fun apiArtistRepository_getArtists_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }, repository.getArtists().first())
        }

    @Test
    fun apiArtistRepository_getArtist_verifyArtist() =
        runTest {
            val repository = FakeApiArtistRepository()
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }.find { it.id == testId }, repository.getArtist(testId).first())
        }

    @Test
    fun apiArtistRepository_searchArtists_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }.filter { it.name == testSearch }, repository.searchArtists(testSearch).first())
        }

    @Test
    fun apiArtistRepository_refresh_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            repository.refresh()
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }, repository.getArtists().first())
        }

    @Test
    fun apiArtistRepository_refreshSearch_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            repository.refreshSearch(testSearch)
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }.filter { it.name == testSearch }, repository.searchArtists(testSearch).first())
        }

    @Test
    fun apiArtistRepository_refreshOne_verifyArtist() =
        runTest {
            val repository = FakeApiArtistRepository()
            repository.refreshOne(testId)
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }.find { it.id == testId }, repository.getArtist(testId).first())
        }
}
