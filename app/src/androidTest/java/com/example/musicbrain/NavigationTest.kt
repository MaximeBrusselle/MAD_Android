package com.example.musicbrain

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.musicbrain.ui.MusicBrain
import com.example.musicbrain.ui.util.MusicBrainNavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test for navigation in the [MusicBrain] app, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NavigationTest {
    /**
     * The ID of an artist to use for testing.
     */
    private val someArtistId: String = "ddf5393e-b242-404c-a141-a546b8357d3d"

    /**
     * The ID of an instrument to use for testing.
     */
    private val someInstrumentId: String = "bf6af06a-5033-4af5-a6b8-7f235b0cff51"

    /**
     * The [UiDevice] to use for testing.
     */
    private lateinit var device: UiDevice

    /**
     * The [Rule] to use for testing.
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * The [TestNavHostController] to use for testing.
     */
    private lateinit var navController: TestNavHostController

    /**
     * Sets up the [MusicBrain] app for testing.
     */
    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MusicBrain(navController = navController, navigationType = MusicBrainNavigationType.BOTTOM_NAVIGATION)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    /**
     * Verifies that initially the artists screen is displayed.
     */
    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
    }

    /**
     * Verifies the navigation to the instruments screen.
     */
    @Test
    fun navigateToInstruments() {
        composeTestRule
            .onNodeWithTag("NavigateToInstrumentsBottom")
            .performClick()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
    }

    /**
     * Verifies the navigation back to the artists screen.
     */
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

    /**
     * Verifies the navigation back to the artists screen using the back button of the device.
     */
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

    /**
     * Verifies the navigation to the artist detail screen.
     */
    @Test
    fun navigateToArtistDetail() {
        Thread.sleep(1000)
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

    /**
     * Verifies the navigation back to the artists screen using the back button of the page.
     */
    @Test
    fun navigateBackFromArtistDetailUsingBackArrow() {
        Thread.sleep(1000)
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("Artist-$someArtistId")
            .performClick()
        composeTestRule
            .onNodeWithTag("DetailImage")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("BackButton")
            .assertIsDisplayed()
            .performClick()
        composeTestRule
            .onNodeWithTag("Artists")
            .assertIsDisplayed()
    }

    /**
     * Verifies the navigation back to the artists screen using the back button of the device.
     */
    @Test
    fun navigateBackFromArtistDetailUsingDevice() {
        Thread.sleep(1000)
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

    /**
     * Verifies the navigation to the instrument detail screen.
     */
    @Test
    fun navigateToInstrumentDetail() {
        Thread.sleep(1000)
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

    /**
     * Verifies the navigation back to the instruments screen using the back button of the device.
     */
    @Test
    fun navigateBackFromInstrumentDetailUsingDevice() {
        Thread.sleep(1000)
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

    /**
     * Verifies the navigation back to the instruments screen using the back button of the page.
     */
    @Test
    fun navigateBackFromInstrumentDetailUsingBackArrow() {
        Thread.sleep(1000)
        Thread.sleep(1000)
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
        composeTestRule
            .onNodeWithTag("BackButton")
            .assertIsDisplayed()
            .performClick()
        composeTestRule
            .onNodeWithTag("Instruments")
            .assertIsDisplayed()
    }
}
