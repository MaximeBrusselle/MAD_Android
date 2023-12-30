package com.example.musicbrain

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.musicbrain.ui.MusicBrain
import com.example.musicbrain.ui.util.MusicBrainNavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    private val someArtistSearchQuery: String = "Test"
    private val someInstrumentSearchQuery: String = "piano"

    private val someArtistId: String = "ddf5393e-b242-404c-a141-a546b8357d3d"
    private val someInstrumentId: String = "bf6af06a-5033-4af5-a6b8-7f235b0cff51"

    private lateinit var device: UiDevice

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MusicBrain(navController = navController, navigationType = MusicBrainNavigationType.BOTTOM_NAVIGATION)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToInstruments() {
        composeTestRule
            .onNodeWithTag("NavigateToInstrumentsBottom")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToArtists() {
        composeTestRule
            .onNodeWithTag("NavigateToInstrumentsBottom")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("NavigateToArtistsBottom")
            .performClick()
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
    }

    @Test
    fun navigateBackToArtists() {
        composeTestRule
            .onNodeWithTag("NavigateToInstrumentsBottom")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
        device.pressBack()
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToArtistDetail() {
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("Artist-$someArtistId")
            .performClick()
        composeTestRule
            .onNodeWithTag("DetailImage")
            .assertIsDisplayed()
    }

    @Test
    fun navigateBackFromArtistDetail() {
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("Artist-$someArtistId")
            .performClick()
        composeTestRule
            .onNodeWithTag("DetailImage")
            .assertIsDisplayed()
        device.pressBack()
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToInstrumentDetail() {
        composeTestRule
            .onNodeWithTag("NavigateToInstrumentsBottom")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("Instrument-$someInstrumentId")
            .performClick()
        composeTestRule
            .onNodeWithTag("DetailImage")
            .assertIsDisplayed()
    }

    @Test
    fun navigateBackFromInstrumentDetail() {
        composeTestRule
            .onNodeWithTag("NavigateToInstrumentsBottom")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("Instrument-$someInstrumentId")
            .performClick()
        composeTestRule
            .onNodeWithTag("DetailImage")
            .assertIsDisplayed()
        device.pressBack()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
    }
}
