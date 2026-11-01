package com.example.privacyawareness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.privacyawareness.ui.components.BottomNavBar
import com.example.privacyawareness.ui.navigation.Destination
import com.example.privacyawareness.ui.screens.AppsScreen
import com.example.privacyawareness.ui.screens.InsightsScreen
import com.example.privacyawareness.ui.screens.PoliciesScreen
import com.example.privacyawareness.ui.screens.SettingsScreen
import com.example.privacyawareness.ui.theme.PrivacyAwarenessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrivacyAwarenessTheme {
                // The back stack: the bottom item is the current tab,
                // anything pushed on top is a "details" screen within that tab.
                val backStack = remember { mutableStateListOf<Destination>(Destination.Apps) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            selectedDestination = backStack.first(),
                            onDestinationSelected = { destination ->
                                // Switching tabs resets any details screen that was pushed.
                                backStack.clear()
                                backStack.add(destination)
                            }
                        )
                    }
                ) { innerPadding ->
                    NavDisplay(
                        modifier = Modifier.padding(innerPadding),
                        backStack = backStack,
                        onBack = {
                            if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
                        },
                        entryProvider = { destination ->
                            when (destination) {
                                Destination.Apps -> NavEntry(destination) { AppsScreen() }
                                Destination.Insights -> NavEntry(destination) { InsightsScreen() }
                                Destination.Policies -> NavEntry(destination) { PoliciesScreen() }
                                Destination.Settings -> NavEntry(destination) { SettingsScreen() }
                            }
                        }
                    )
                }
            }
        }
    }
}