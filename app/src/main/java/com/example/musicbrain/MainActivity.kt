package com.example.musicbrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.musicbrain.ui.MusicBrain
import com.example.musicbrain.ui.theme.MusicBrainTheme
import com.example.musicbrain.ui.util.MusicBrainNavigationType

/**
 * Main activity for the MusicBrain application.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting.
     */
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicBrainTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background,
                ) {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            MusicBrain(MusicBrainNavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            MusicBrain(MusicBrainNavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            MusicBrain(navigationType = MusicBrainNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            MusicBrain(navigationType = MusicBrainNavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}
