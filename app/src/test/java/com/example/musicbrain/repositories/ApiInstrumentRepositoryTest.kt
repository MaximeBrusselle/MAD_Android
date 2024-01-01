package com.example.musicbrain.repositories

import com.example.musicbrain.fake.FakeApiInstrumentRepository
import com.example.musicbrain.fake.FakeDataSource
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for [ApiInstrumentRepository]
 */
class ApiInstrumentRepositoryTest {
    /**
     * The ID of an instrument to use for testing.
     */
    private var testId = "b3eac5f9-7859-4416-ac39-7154e2e8d348"

    /**
     * The search term to use for testing.
     */
    private var testSearch = "piano"

    /**
     * Tests [ApiInstrumentRepository.getInstruments]
     */
    @Test
    fun apiInstrumentRepository_getInstruments_verifyInstrumentsList() =
        runTest {
            val repository = FakeApiInstrumentRepository()
            assertEquals(FakeDataSource.apiInstruments.map { it.asDomainObject() }, repository.getInstruments().first())
        }

    /**
     * Tests [ApiInstrumentRepository.getInstrument]
     */
    @Test
    fun apiInstrumentRepository_getInstrument_verifyInstrument() =
        runTest {
            val repository = FakeApiInstrumentRepository()
            assertEquals(
                FakeDataSource.apiInstruments.map { it.asDomainObject() }.find { it.id == testId },
                repository.getInstrument(testId).first(),
            )
        }

    /**
     * Tests [ApiInstrumentRepository.searchInstruments]
     */
    @Test
    fun apiInstrumentRepository_searchInstruments_verifyInstrumentsList() =
        runTest {
            val repository = FakeApiInstrumentRepository()
            assertEquals(
                FakeDataSource.apiInstruments.map {
                    it.asDomainObject()
                }.filter { it.name == testSearch },
                repository.searchInstruments(testSearch).first(),
            )
        }

    /**
     * Tests [ApiInstrumentRepository.refresh]
     */
    @Test
    fun apiInstrumentRepository_refresh_verifyInstrumentsList() =
        runTest {
            val repository = FakeApiInstrumentRepository()
            repository.refresh()
            assertEquals(FakeDataSource.apiInstruments.map { it.asDomainObject() }, repository.getInstruments().first())
        }

    /**
     * Tests [ApiInstrumentRepository.refreshSearch]
     */
    @Test
    fun apiInstrumentRepository_refreshSearch_verifyInstrumentsList() =
        runTest {
            val repository = FakeApiInstrumentRepository()
            repository.refreshSearch(testSearch)
            assertEquals(
                FakeDataSource.apiInstruments.map {
                    it.asDomainObject()
                }.filter { it.name == testSearch },
                repository.searchInstruments(testSearch).first(),
            )
        }

    /**
     * Tests [ApiInstrumentRepository.refreshOne]
     */
    @Test
    fun apiInstrumentRepository_refreshOne_verifyInstrument() =
        runTest {
            val repository = FakeApiInstrumentRepository()
            repository.refreshOne(testId)
            assertEquals(
                FakeDataSource.apiInstruments.map { it.asDomainObject() }.find { it.id == testId },
                repository.getInstrument(testId).first(),
            )
        }
}
