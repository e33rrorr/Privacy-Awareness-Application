package com.example.privacyawareness.data.network
import com.google.gson.annotations.SerializedName

data class AppRiskResponse(
    @SerializedName("package_name")
    val packageName: String,

    // "low", "medium" or "high"
    @SerializedName("risk")
    val risk: String
)
