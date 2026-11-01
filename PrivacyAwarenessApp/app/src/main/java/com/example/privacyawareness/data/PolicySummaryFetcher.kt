package com.example.privacyawareness.data
import android.util.Log
import com.example.privacyawareness.data.network.PolicySummaryResponse
import com.example.privacyawareness.data.network.RetrofitClient
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "PolicySummaryFetcher"

// Gets the AI summary of an app's privacy policy, or null if the request fails.
suspend fun fetchPolicySummary(packageName: String): PolicySummaryResponse? {

    return try {
        RetrofitClient.scanApi.getPolicySummary(packageName)

    } catch (e: IOException) {
        Log.e(TAG, "Could not get the policy summary from the backend", e)
        null

    } catch (e: HttpException) {
        Log.e(TAG, "Backend returned an error: ${e.code()}", e)
        null
    }
}
