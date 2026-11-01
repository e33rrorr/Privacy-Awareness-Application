package com.example.privacyawareness.data
import android.util.Log
import com.example.privacyawareness.data.network.RetrofitClient
import com.example.privacyawareness.model.RiskLevel
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "RiskScoreFetcher"


// Gets each app's risk level from the backend, or null if the request fails.
suspend fun fetchRiskLevels(): Map<String, RiskLevel>? {

    return try {
        val scores = RetrofitClient.scanApi.getRiskScores()

        val riskLevels = mutableMapOf<String, RiskLevel>()
        scores.forEach { score ->
            val riskLevel = toRiskLevel(score.risk)
            if (riskLevel != null) {
                riskLevels[score.packageName] = riskLevel
            }
        }
        riskLevels

    } catch (e: IOException) {
        Log.e(TAG, "Could not get risk scores from the backend", e)
        null

    } catch (e: HttpException) {
        Log.e(TAG, "Backend returned an error: ${e.code()}", e)
        null
    }
}


// Converts the backend's text ("low", "medium", "high") into our RiskLevel.
private fun toRiskLevel(risk: String): RiskLevel? {

    return when (risk) {
        "low" -> RiskLevel.LOW
        "medium" -> RiskLevel.MEDIUM
        "high" -> RiskLevel.HIGH
        else -> null
    }
}
