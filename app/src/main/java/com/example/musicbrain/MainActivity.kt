package com.example.musicbrain

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import com.example.musicbrain.ui.MusicBrain
import com.example.musicbrain.ui.theme.MusicBrainTheme
//import com.example.musicbrain.ui.util.TaskNavigationType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*//added because the app grows. The dex file is a Dalvic Executable (a part of the compilation process of Android)
        //if it becomes to large, the OS has issues handling it well...
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()*/
        Log.i("vm inspection", "Main activity onCreate")
        setContent {
            MusicBrainTheme {
                // create a Surface to overlap image and texts
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    //Image(painter = painterResource(id = R.drawable.musicbrain), contentDescription = "MusicBrain", contentScale = ContentScale.Crop)
                    MusicBrain()
                }
            }
        }
    }
}
