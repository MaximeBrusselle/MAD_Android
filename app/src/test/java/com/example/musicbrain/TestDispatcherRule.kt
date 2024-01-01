package com.example.musicbrain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A test rule that swaps the main dispatcher for a test dispatcher.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    /**
     * sets the main dispatcher to the test dispatcher
     */
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * resets the main dispatcher
     */
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
