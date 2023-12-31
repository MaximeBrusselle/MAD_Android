package com.example.musicbrain.viewmodels

import com.example.musicbrain.TestDispatcherRule
import com.example.musicbrain.fake.FakeApiInstrumentRepository
import com.example.musicbrain.ui.instrumentsScreen.InstrumentsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InstrumentsViewModelTest {
    private val searchQuery = "piano"
    private lateinit var viewModel: InstrumentsViewModel


    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun setUp() {
        viewModel =
            InstrumentsViewModel(
                instrumentRepository = FakeApiInstrumentRepository(),
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
    fun searchingInstrumentsChangesState() {
        viewModel.updateQuery(searchQuery)
        viewModel.searchInstruments()
        Assert.assertEquals(viewModel.uiState.value.searchHistory[0], searchQuery)
    }
}