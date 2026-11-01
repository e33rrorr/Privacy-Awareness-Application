package com.example.privacyawareness.data.network
import com.example.privacyawareness.model.PermissionDetail
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface ScanApi {
    @Multipart
    @POST("scan")
    suspend fun uploadPermissionsFile(@Part file: MultipartBody.Part)

    @GET("risk_scoring")
    suspend fun getRiskScores(): List<AppRiskResponse>

    @GET("permissions")
    suspend fun getPermissionDetails(): Map<String, PermissionDetail>

    @GET("policy_summary/{packageName}")
    suspend fun getPolicySummary(@Path("packageName") packageName: String): PolicySummaryResponse
}


