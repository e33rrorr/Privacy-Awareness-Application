package com.example.privacyawareness.data
import android.util.Log
import com.example.privacyawareness.data.network.RetrofitClient
import com.example.privacyawareness.model.PermissionDetail
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "PermissionDetailsFetcher"

// Gets the details of every permission from the backend, or null if the request fails.
suspend fun fetchPermissionDetails(): Map<String, PermissionDetail>? {

    return try {
        RetrofitClient.scanApi.getPermissionDetails()

    } catch (e: IOException) {
        Log.e(TAG, "Could not get permission details from the backend", e)
        null

    } catch (e: HttpException) {
        Log.e(TAG, "Backend returned an error: ${e.code()}", e)
        null
    }
}
