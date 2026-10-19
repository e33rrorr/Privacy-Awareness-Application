package com.example.privacyawareness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.ui.components.AppFilter
import com.example.privacyawareness.ui.components.AppListItem
import com.example.privacyawareness.ui.components.FilterChipsRow
import com.example.privacyawareness.ui.model.AppInfo
import com.example.privacyawareness.ui.model.RiskLevel

// Placeholder data until this screen reads real installed-app info.
private val sampleApps = listOf(
    AppInfo(
        name = "PhotoShare",
        icon = Icons.Default.PhotoCamera,
        iconBackgroundColor = Color(0xFF8E24AA),
        permissionCount = 12,
        riskLevel = RiskLevel.HIGH
    ),
    AppInfo(
        name = "ChatConnect",
        icon = Icons.AutoMirrored.Filled.Chat,
        iconBackgroundColor = Color(0xFF1E88E5),
        permissionCount = 10,
        riskLevel = RiskLevel.MEDIUM
    ),
    AppInfo(
        name = "ShopEasy",
        icon = Icons.Default.ShoppingCart,
        iconBackgroundColor = Color(0xFFE64A19),
        permissionCount = 9,
        riskLevel = RiskLevel.HIGH
    ),
    AppInfo(
        name = "MapTrack",
        icon = Icons.Default.LocationOn,
        iconBackgroundColor = Color(0xFF43A047),
        permissionCount = 3,
        riskLevel = RiskLevel.LOW
    )
)

@Composable
fun AppsScreen() {
    // Selection lives here (not inside FilterChipsRow) so a future filtering
    // step can read it alongside sampleApps without touching the chip row itself.
    var selectedFilter by remember { mutableStateOf(AppFilter.ALL) }

    // "All" has no riskLevel, so it passes everything through; the other
    // chips keep only the apps whose riskLevel matches the chip.
    val visibleApps = sampleApps.filter { app ->
        selectedFilter.riskLevel == null || app.riskLevel == selectedFilter.riskLevel
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        FilterChipsRow(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(visibleApps) { app ->
                AppListItem(app)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppsScreenPreview() {
    AppsScreen()
}
