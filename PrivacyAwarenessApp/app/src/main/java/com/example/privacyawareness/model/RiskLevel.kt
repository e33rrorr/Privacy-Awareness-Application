package com.example.privacyawareness.model
import androidx.compose.ui.graphics.Color

enum class RiskLevel(val label: String, val color: Color) {
    LOW("Low Risk", Color(0xFF2E7D32)),
    MEDIUM("Medium Risk", Color(0xFFF9A825)),
    HIGH("High Risk", Color(0xFFD32F2F))
}
