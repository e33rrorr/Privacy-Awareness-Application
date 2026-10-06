package com.example.privacyawareness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.ui.components.AppListItem
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
    )
)

@Composable
fun AppsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleApps) { app ->
            AppListItem(app)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppsScreenPreview() {
    AppsScreen()
}
