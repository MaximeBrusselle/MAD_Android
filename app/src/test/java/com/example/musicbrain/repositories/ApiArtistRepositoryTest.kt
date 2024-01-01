package com.example.musicbrain.repositories

import com.example.musicbrain.fake.FakeApiArtistRepository
import com.example.musicbrain.fake.FakeDataSource
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for [ApiArtistRepository]
 */
class ApiArtistRepositoryTest {
    /**
     * The ID of an artist to use for testing.
     */
    private var testId = "bc810ce5-7c02-4856-a704-ea0126b3ccfa"

    /**
     * The search term to use for testing.
     */
    private var testSearch = "DEEZL"

    /**
     * Tests [ApiArtistRepository.getArtists]
     */
    @Test
    fun apiArtistRepository_getArtists_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }, repository.getArtists().first())
        }

    /**
     * Tests [ApiArtistRepository.getArtist]
     */
    @Test
    fun apiArtistRepository_getArtist_verifyArtist() =
        runTest {
            val repository = FakeApiArtistRepository()
            assertEquals(
                FakeDataSource.apiArtists.map { it.asDomainObject() }.find { it.id == testId },
                repository.getArtist(testId).first(),
            )
        }

    /**
     * Tests [ApiArtistRepository.searchArtists]
     */
    @Test
    fun apiArtistRepository_searchArtists_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            assertEquals(
                FakeDataSource.apiArtists.map {
                    it.asDomainObject()
                }.filter { it.name == testSearch },
                repository.searchArtists(testSearch).first(),
            )
        }

    /**
     * Tests [ApiArtistRepository.refresh]
     */
    @Test
    fun apiArtistRepository_refresh_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            repository.refresh()
            assertEquals(FakeDataSource.apiArtists.map { it.asDomainObject() }, repository.getArtists().first())
        }

    /**
     * Tests [ApiArtistRepository.refreshSearch]
     */
    @Test
    fun apiArtistRepository_refreshSearch_verifyArtistsList() =
        runTest {
            val repository = FakeApiArtistRepository()
            repository.refreshSearch(testSearch)
            assertEquals(
                FakeDataSource.apiArtists.map {
                    it.asDomainObject()
                }.filter { it.name == testSearch },
                repository.searchArtists(testSearch).first(),
            )
        }

    /**
     * Tests [ApiArtistRepository.refreshOne]
     */
    @Test
    fun apiArtistRepository_refreshOne_verifyArtist() =
        runTest {
            val repository = FakeApiArtistRepository()
            repository.refreshOne(testId)
            assertEquals(
                FakeDataSource.apiArtists.map { it.asDomainObject() }.find { it.id == testId },
                repository.getArtist(testId).first(),
            )
        }
}
