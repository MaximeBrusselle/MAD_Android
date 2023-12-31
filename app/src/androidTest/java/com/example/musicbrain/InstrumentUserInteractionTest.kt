package com.example.musicbrain

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.musicbrain.data.InstrumentRepository
import com.example.musicbrain.fake.FakeApiInstrumentRepository
import com.example.musicbrain.ui.instrumentsScreen.InstrumentsScreen
import com.example.musicbrain.ui.instrumentsScreen.InstrumentsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InstrumentUserInteractionTest {

    private val exampleSearchQuery: String = "piano"
    private val searchedTag: String = "Instrument-b3eac5f9-7859-4416-ac39-7154e2e8d348"
    private val someInstrumentId: String = "1c8f9780-2f16-4891-b66d-bb7aa0820dbd"

    private lateinit var device: UiDevice
    private lateinit var fakeRepository: InstrumentRepository
    private lateinit var viewModel: InstrumentsViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupAppNavHost() {
        fakeRepository = FakeApiInstrumentRepository()
        viewModel = InstrumentsViewModel(fakeRepository)
        composeTestRule.setContent {
            InstrumentsScreen(toDetailPage = {}, instrumentsViewModel = viewModel)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun verifyInstrumentsScreenHasSearchbar() {
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .assertIsDisplayed()
    }

    @Test
    fun searchHistoryIsInitallyEmpty(){
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchHistoryEmpty")
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchInstrumentGivesCorrectResult() {
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        device.pressEnter()
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchHistoryResultsInQuery() {
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        device.pressEnter()
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
        composeTestRule
            .onNodeWithTag("InstrumentsSearchClear")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instrument-$someInstrumentId")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(1)
            .performClick()
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
    }

    @Test
    fun verifyCancelButtonWorks() {
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        composeTestRule
            .onNodeWithTag("InstrumentsSearchClear")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchClear")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instrument-$someInstrumentId")
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyCancelButtonWorksAfterSearch() {
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        device.pressEnter()
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
        composeTestRule
            .onNodeWithTag("InstrumentsSearchClear")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("InstrumentsSearchClear")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instrument-$someInstrumentId")
            .assertIsDisplayed()
    }
}