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

/**
 * Instrumented test to test all user interaction for [InstrumentsScreen], which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class InstrumentUserInteractionTest {
    /**
     * The example search query to use for testing.
     */
    private val exampleSearchQuery: String = "piano"

    /**
     * The tag to use for the searched instrument.
     */
    private val searchedTag: String = "Instrument-b3eac5f9-7859-4416-ac39-7154e2e8d348"

    /**
     * The ID of an instrument to use for testing.
     */
    private val someInstrumentId: String = "1c8f9780-2f16-4891-b66d-bb7aa0820dbd"

    /**
     * The [UiDevice] to use for testing.
     */
    private lateinit var device: UiDevice

    /**
     * The fake repository to use for testing.
     */
    private lateinit var fakeRepository: InstrumentRepository

    /**
     * The [InstrumentsViewModel] to use for testing.
     */
    private lateinit var viewModel: InstrumentsViewModel

    /**
     * The [Rule] to use for testing.
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Setup method to initialize the fake repository, view model, and set up the ComposeTestRule
     * with the [InstrumentsScreen].
     */
    @Before
    fun setupAppNavHost() {
        fakeRepository = FakeApiInstrumentRepository()
        viewModel = InstrumentsViewModel(fakeRepository)
        composeTestRule.setContent {
            InstrumentsScreen(toDetailPage = {}, instrumentsViewModel = viewModel)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    /**
     * Test to verify that the InstrumentsScreen contains a search bar, and it is initially displayed.
     */
    @Test
    fun verifyInstrumentsScreenHasSearchbar() {
        composeTestRule
            .onNodeWithTag("InstrumentsSearchBar")
            .assertIsDisplayed()
    }

    /**
     * Test to verify that the search history is initially empty.
     */
    @Test
    fun searchHistoryIsInitiallyEmpty() {
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

    /**
     * Test to verify that searching for an instrument gives the correct result.
     */
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

    /**
     * Test to verify that searching for an instrument adds it to the search history.
     * Also tests if pressing on a history item results in a search.
     */
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

    /**
     * Test to verify that the cancel button works while searching.
     */
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

    /**
     * Test to verify that the cancel button works after performing a search.
     */
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
