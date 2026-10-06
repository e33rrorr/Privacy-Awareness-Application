package com.example.privacyawareness.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class RiskLevel(val label: String, val color: Color) {
    LOW("Low Risk", Color(0xFF2E7D32)),
    MEDIUM("Medium Risk", Color(0xFFF9A825)),
    HIGH("High Risk", Color(0xFFD32F2F))
}

data class AppInfo(
    val name: String,
    val icon: ImageVector,
    val iconBackgroundColor: Color,
    val permissionCount: Int,
    val riskLevel: RiskLevel
)
