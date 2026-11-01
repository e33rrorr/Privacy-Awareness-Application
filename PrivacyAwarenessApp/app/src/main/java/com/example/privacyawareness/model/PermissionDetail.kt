package com.example.privacyawareness.model
import com.google.gson.annotations.SerializedName

// What the backend's knowledge base says about one permission.
data class PermissionDetail(
    val category: String,
    val description: String,

    @SerializedName("what_to_consider")
    val whatToConsider: String?
)
