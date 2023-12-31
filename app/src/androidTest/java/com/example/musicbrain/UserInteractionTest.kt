package com.example.musicbrain

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.musicbrain.ui.MusicBrain
import com.example.musicbrain.ui.util.MusicBrainNavigationType
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserInteractionTest {

    private val exampleSearchQuery: String = "Acid Test"
    private val searchedTag: String = "Artist-183c855d-559a-4dda-96c5-0deaf2b1309e"
    private val someArtistId: String = "ddf5393e-b242-404c-a141-a546b8357d3d"
    private val someInstrumentSearchQuery: String = "piano"

    private lateinit var device: UiDevice

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MusicBrain(navController = navController, navigationType = MusicBrainNavigationType.BOTTOM_NAVIGATION)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun verifyArtistsScreenHasSearchbar() {
        setupAppNavHost()
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("ArtistsSearchBar")
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchArtistGivesCorrectResult() {
        setupAppNavHost()
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
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
        setupAppNavHost()
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
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
        //make sure data is loaded
        composeTestRule
            .waitUntilAtLeastOneExists(
                hasTestTag(searchedTag),
            )
    }

    @Test
    fun verifyCancelButtonWorks() {
        setupAppNavHost()
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
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
        composeTestRule
            .onNodeWithTag("Artist-$someArtistId")
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyCancelButtonWorksAfterSearch() {
        setupAppNavHost()
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
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