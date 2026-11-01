package com.example.privacyawareness.data.network
import com.google.gson.annotations.SerializedName


data class PolicySummaryResponse(

    @SerializedName("package_name")
    val packageName: String,

    @SerializedName("summary")
    val summary: List<PolicySection>?,

    @SerializedName("policy_url")
    val policyUrl: String?
)
