package com.example.privacyawareness
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.privacyawareness.data.fetchPermissionDetails
import com.example.privacyawareness.data.fetchRiskLevels
import com.example.privacyawareness.data.loadInstalledApps
import com.example.privacyawareness.data.uploadPermissionFiles
import com.example.privacyawareness.data.writePermissionsJson
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.model.PermissionDetail
import com.example.privacyawareness.ui.components.BottomNavBar
import com.example.privacyawareness.ui.navigation.Destination
import com.example.privacyawareness.ui.screens.AppInsightsScreen
import com.example.privacyawareness.ui.screens.AppsScreen
import com.example.privacyawareness.ui.screens.PoliciesScreen
import com.example.privacyawareness.ui.screens.PolicySummaryScreen
import com.example.privacyawareness.ui.theme.PrivacyAwarenessTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : ComponentActivity() {
    // Starts the app: holds the scan results and switches between screens.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val permissionsFolder = File(filesDir, "permissions")

        setContent {
            PrivacyAwarenessTheme {
                var installedApps by remember { mutableStateOf<List<AppInfo>?>(null) }
                var isScanning by remember { mutableStateOf(false) }
                var lastScanTime by remember { mutableStateOf<Long?>(null) }
                var permissionDetails by remember {
                    mutableStateOf<Map<String, PermissionDetail>>(emptyMap())
                }
                val scope = rememberCoroutineScope()

                // Scans the phone's apps, sends them to the backend and shows the results.
                val onScanClick: () -> Unit = {
                    scope.launch {
                        isScanning = true

                        val apps = withContext(Dispatchers.IO) {
                            loadInstalledApps(packageManager)
                        }

                        val files = withContext(Dispatchers.IO) {
                            apps.map { app -> writePermissionsJson(app, permissionsFolder) }
                        }

                        val uploaded = uploadPermissionFiles(files)
                        val riskLevels = if (uploaded) fetchRiskLevels() else null
                        val details = if (riskLevels != null) fetchPermissionDetails() else null

                        // Only show the apps once the backend has returned its results.
                        if (riskLevels != null && details != null) {
                            installedApps = apps.map { app ->
                                app.copy(riskLevel = riskLevels[app.packageName])
                            }
                            permissionDetails = details
                            lastScanTime = System.currentTimeMillis()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Could not reach the server",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        isScanning = false
                    }
                }

                val backStack = remember { mutableStateListOf<Destination>(Destination.Apps) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            selectedDestination = backStack.first(),
                            onDestinationSelected = { destination ->
                                // Switching tabs resets any details screen that was pushed.
                                backStack.clear()
                                backStack.add(destination)
                            }
                        )
                    }
                ) { innerPadding ->
                    NavDisplay(
                        modifier = Modifier.padding(innerPadding),
                        backStack = backStack,
                        onBack = {
                            if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
                        },
                        entryProvider = { destination ->
                            when (destination) {
                                Destination.Apps -> NavEntry(destination) {
                                    AppsScreen(
                                        apps = installedApps,
                                        lastScanTime = lastScanTime,
                                        isScanning = isScanning,
                                        onScanClick = onScanClick,
                                        onAppClick = { app ->
                                            backStack.add(Destination.AppInsights(app.packageName))
                                        }
                                    )
                                }
                                Destination.Policies -> NavEntry(destination) {
                                    PoliciesScreen(
                                        apps = installedApps,
                                        onAppClick = { app ->
                                            backStack.add(Destination.PolicySummary(app.packageName))
                                        }
                                    )
                                }

                                is Destination.AppInsights -> NavEntry(destination) {
                                    val app = installedApps?.find { app ->
                                        app.packageName == destination.packageName
                                    }
                                    if (app != null) {
                                        AppInsightsScreen(
                                            app = app,
                                            permissionDetails = permissionDetails,
                                            onBack = { backStack.removeAt(backStack.lastIndex) }
                                        )
                                    }
                                }

                                is Destination.PolicySummary -> NavEntry(destination) {
                                    val app = installedApps?.find { app ->
                                        app.packageName == destination.packageName
                                    }
                                    if (app != null) {
                                        PolicySummaryScreen(
                                            app = app,
                                            onBack = { backStack.removeAt(backStack.lastIndex) }
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
