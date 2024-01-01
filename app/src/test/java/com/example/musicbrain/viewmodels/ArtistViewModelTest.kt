package com.example.musicbrain.viewmodels

import com.example.musicbrain.TestDispatcherRule
import com.example.musicbrain.fake.FakeApiArtistRepository
import com.example.musicbrain.ui.artistsScreen.ArtistsApiState
import com.example.musicbrain.ui.artistsScreen.ArtistsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtistsViewModelTest {
    private val searchQuery = "DEEZL"
    private lateinit var viewModel: ArtistsViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun setUp() {
        viewModel =
            ArtistsViewModel(
                artistRepository = FakeApiArtistRepository(),
            )
    }

    @Test
    fun settingSearchChangesState() {
        viewModel.updateQuery(searchQuery)
        Assert.assertEquals(viewModel.uiState.value.query, searchQuery)
    }

    @Test
    fun clearingSearchChangesState() {
        viewModel.updateQuery(searchQuery)
        viewModel.clearQuery()
        Assert.assertEquals(viewModel.uiState.value.query, "")
    }

    @Test
    fun settingActiveChangesState() {
        viewModel.setActive(false)
        Assert.assertEquals(viewModel.uiState.value.active, false)
    }

    @Test
    fun searchingArtistsChangesState() {
        viewModel.updateQuery(searchQuery)
        viewModel.searchArtists()
        Assert.assertEquals(viewModel.uiState.value.searchHistory[0], searchQuery)
    }

    @Test
    fun stateAfterInitShouldBeSuccess() {
        Assert.assertEquals(viewModel.artistsApiState, ArtistsApiState.Success)
    }

    @Test
    fun stateAfterSearchShouldBeSuccess() {
        viewModel.updateQuery(searchQuery)
        viewModel.searchArtists()
        Assert.assertEquals(viewModel.artistsApiState, ArtistsApiState.Success)
    }
}
