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

class ArtistUserInteractionTest {
    private val exampleSearchQuery: String = "DEEZL"
    private val searchedTag: String = "Artist-bc810ce5-7c02-4856-a704-ea0126b3ccfa"
    private val someArtistId: String = "1b05edc2-6fb2-4242-a8f4-d6f9795cbc7a"

    private lateinit var device: UiDevice
    private lateinit var fakeRepository: ArtistRepository
    private lateinit var viewModel: ArtistsViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupAppNavHost() {
        fakeRepository = FakeApiArtistRepository()
        viewModel = ArtistsViewModel(fakeRepository)

        composeTestRule.setContent {
            ArtistsScreen(toDetailPage = {}, artistsViewModel = viewModel)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun verifyArtistsScreenHasSearchbar() {
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
    }

    @Test
    fun searchHistoryIsInitallyEmpty() {
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
