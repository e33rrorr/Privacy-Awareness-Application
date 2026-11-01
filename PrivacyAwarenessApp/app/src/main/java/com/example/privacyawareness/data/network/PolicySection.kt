package com.example.privacyawareness.data.network
import com.google.gson.annotations.SerializedName


data class PolicySection(
    @SerializedName("key")
    val key: String,

    @SerializedName("question")
    val question: String,

    @SerializedName("points")
    val points: List<String>
)
