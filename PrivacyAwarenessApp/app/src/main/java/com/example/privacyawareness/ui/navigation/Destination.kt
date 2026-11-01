package com.example.privacyawareness.ui.navigation

// Every screen the app can navigate to is one of these.
// Using a sealed interface means the compiler can check we handled every case.
sealed interface Destination {
    data object Apps : Destination
    data object Insights : Destination
    data object Policies : Destination
    data object Settings : Destination
}
