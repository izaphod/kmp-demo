package com.sequenia.kmp.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

private const val SCRIM_COLOR = Color.TRANSPARENT

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = SCRIM_COLOR),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = SCRIM_COLOR,
                darkScrim = SCRIM_COLOR
            )
        )

        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}