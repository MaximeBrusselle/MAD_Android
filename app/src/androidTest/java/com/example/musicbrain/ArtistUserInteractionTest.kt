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
import com.example.musicbrain.data.ArtistRepository
import com.example.musicbrain.fake.FakeApiArtistRepository
import com.example.musicbrain.ui.artistsScreen.ArtistsScreen
import com.example.musicbrain.ui.artistsScreen.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test to test all user interaction for [ArtistsScreen], which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ArtistUserInteractionTest {
    /**
     * The example search query to use for testing.
     */
    private val exampleSearchQuery: String = "DEEZL"

    /**
     * The tag to use for the searched artist.
     */
    private val searchedTag: String = "Artist-bc810ce5-7c02-4856-a704-ea0126b3ccfa"

    /**
     * The ID of an artist to use for testing.
     */
    private val someArtistId: String = "1b05edc2-6fb2-4242-a8f4-d6f9795cbc7a"

    /**
     * The [UiDevice] to use for testing.
     */
    private lateinit var device: UiDevice

    /**
     * The fake repository to use for testing.
     */
    private lateinit var fakeRepository: ArtistRepository

    /**
     * The [ArtistsViewModel] to use for testing.
     */
    private lateinit var viewModel: ArtistsViewModel

    /**
     * The [Rule] to use for testing.
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Setup method to initialize the fake repository, view model, and set up the ComposeTestRule
     * with the [ArtistsScreen].
     */
    @Before
    fun setUpApp() {
        fakeRepository = FakeApiArtistRepository()
        viewModel = ArtistsViewModel(fakeRepository)

        composeTestRule.setContent {
            ArtistsScreen(toDetailPage = {}, artistsViewModel = viewModel)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    /**
     * Test to verify that the ArtistsScreen contains a search bar, and it is initially displayed.
     */
    @Test
    fun verifyArtistsScreenHasSearchbar() {
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
    }

    /**
     * Test to verify that the search history is initially empty.
     */
    @Test
    fun searchHistoryIsInitiallyEmpty() {
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("ArtistsSearchHistoryEmpty")
            .assertIsDisplayed()
    }

    /**
     * Test to verify that searching for an artist gives the correct result.
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchArtistGivesCorrectResult() {
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        device.pressEnter()
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
    }

    /**
     * Test to verify that searching for an artist adds it to the search history.
     * Also tests if pressing on a history item results in a search.
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchHistoryResultsInQuery() {
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        device.pressEnter()
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
        composeTestRule
            .onNodeWithTag("ArtistsSearchClear")
            .performClick()
        composeTestRule
            .onNodeWithTag("Artist-$someArtistId")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(1)
            .performClick()
        Thread.sleep(1000)
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
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        composeTestRule
            .onNodeWithTag("ArtistsSearchClear")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchClear")
            .performClick()
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("Artist-$someArtistId")
            .assertIsDisplayed()
    }

    /**
     * Test to verify that the cancel button works after performing a search.
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyCancelButtonWorksAfterSearch() {
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .performClick()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextClearance()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .onChildAt(0)
            .performTextInput(exampleSearchQuery)
        device.pressEnter()
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
        composeTestRule
            .onNodeWithTag("ArtistsSearchClear")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchClear")
            .performClick()
        composeTestRule
            .onNodeWithTag("Artist-$someArtistId")
            .assertIsDisplayed()
    }
}
