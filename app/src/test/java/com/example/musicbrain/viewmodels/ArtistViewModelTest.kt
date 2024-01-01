package com.example.musicbrain.viewmodels

import com.example.musicbrain.TestDispatcherRule
import com.example.musicbrain.fake.FakeApiArtistRepository
import com.example.musicbrain.ui.artistsScreen.ArtistsApiState
import com.example.musicbrain.ui.artistsScreen.ArtistsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [ArtistsViewModel]
 */
class ArtistsViewModelTest {
    /**
     * the search query to be used in the tests
     */
    private val searchQuery = "DEEZL"

    /**
     * the view model to be tested
     */
    private lateinit var viewModel: ArtistsViewModel

    /**
     * the test dispatcher rule to be used in the tests
     */
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    /**
     * sets up the view model to be tested
     */
    @Before
    fun setUp() {
        viewModel =
            ArtistsViewModel(
                artistRepository = FakeApiArtistRepository(),
            )
    }

    /**
     * tests that the view models state is updated after searching
     */
    @Test
    fun settingSearchChangesState() {
        viewModel.updateQuery(searchQuery)
        Assert.assertEquals(viewModel.uiState.value.query, searchQuery)
    }

    /**
     * tests that the view models state is updated after clearing the search
     */
    @Test
    fun clearingSearchChangesState() {
        viewModel.updateQuery(searchQuery)
        viewModel.clearQuery()
        Assert.assertEquals(viewModel.uiState.value.query, "")
    }

    /**
     * tests that the view models state is updated after setting the active state
     */
    @Test
    fun settingActiveChangesState() {
        viewModel.setActive(false)
        Assert.assertEquals(viewModel.uiState.value.active, false)
    }

    /**
     * tests that the view models state is updated searching artists
     */
    @Test
    fun searchingArtistsChangesState() {
        viewModel.updateQuery(searchQuery)
        viewModel.searchArtists()
        Assert.assertEquals(viewModel.uiState.value.searchHistory[0], searchQuery)
    }

    /**
     * tests that the view models apiState is success after init
     */
    @Test
    fun stateAfterInitShouldBeSuccess() {
        Assert.assertEquals(viewModel.artistsApiState, ArtistsApiState.Success)
    }

    /**
     * tests that the view models apiState is success after searching
     */
    @Test
    fun stateAfterSearchShouldBeSuccess() {
        viewModel.updateQuery(searchQuery)
        viewModel.searchArtists()
        Assert.assertEquals(viewModel.artistsApiState, ArtistsApiState.Success)
    }
}
