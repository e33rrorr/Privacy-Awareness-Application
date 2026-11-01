package com.example.privacyawareness.data
import android.util.Log
import com.example.privacyawareness.data.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

private const val TAG = "PermissionUploader"


// Uploads each JSON file to the backend. Returns false if any upload fails.
suspend fun uploadPermissionFiles(files: List<File>): Boolean {

    return try {
        files.forEach { file ->
            uploadPermissionsFile(file)
        }
        true

    } catch (e: IOException) {
        Log.e(TAG, "Could not reach the backend", e)
        false

    } catch (e: HttpException) {
        Log.e(TAG, "Backend returned an error: ${e.code()}", e)
        false
    }
}


// Sends one file to the backend's /scan endpoint.
private suspend fun uploadPermissionsFile(file: File) {

    val body = file.asRequestBody("application/json".toMediaType())
    val part = MultipartBody.Part.createFormData("file", file.name, body)

    RetrofitClient.scanApi.uploadPermissionsFile(part)
}
