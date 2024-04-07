package com.artushock.apps.spillme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.artushock.apps.spillme.ui.navigation.AppNavigation
import com.artushock.apps.spillme.ui.theme.SpillMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpillMeTheme(
                dynamicColor = false
            ) { AppNavigation() }
        }
    }
}