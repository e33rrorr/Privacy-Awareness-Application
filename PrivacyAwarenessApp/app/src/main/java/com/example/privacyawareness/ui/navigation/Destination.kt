package com.example.privacyawareness.ui.navigation

sealed interface Destination {
    data object Apps : Destination
    data object Policies : Destination

    data class AppInsights(val packageName: String) : Destination

    data class PolicySummary(val packageName: String) : Destination
}
