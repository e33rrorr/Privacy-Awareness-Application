package com.example.privacyawareness.model
import androidx.compose.ui.graphics.ImageBitmap

data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: ImageBitmap,
    val permissions: List<PermissionInfo>,
    val riskLevel: RiskLevel? = null
)
