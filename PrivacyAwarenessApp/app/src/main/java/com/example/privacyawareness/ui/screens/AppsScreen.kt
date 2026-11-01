package com.example.privacyawareness.ui.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.ui.components.AppFilter
import com.example.privacyawareness.ui.components.AppListItem
import com.example.privacyawareness.ui.components.FilterChipsRow
import com.example.privacyawareness.ui.components.ScanEmptyState
import com.example.privacyawareness.ui.theme.PrivacyAwarenessTheme


// Screen that lists the scanned apps and their risk levels.
@Composable
fun AppsScreen(
    apps: List<AppInfo>?,
    lastScanTime: Long?,
    isScanning: Boolean,
    onScanClick: () -> Unit,
    onAppClick: (AppInfo) -> Unit
) {
    if (apps == null) {
        ScanEmptyState(isScanning = isScanning, onScanClick = onScanClick)
    } else {
        InstalledAppsContent(
            apps = apps,
            lastScanTime = lastScanTime,
            isScanning = isScanning,
            onRescanClick = onScanClick,
            onAppClick = onAppClick
        )
    }
}

@Composable
private fun InstalledAppsContent(
    apps: List<AppInfo>,
    lastScanTime: Long?,
    isScanning: Boolean,
    onRescanClick: () -> Unit,
    onAppClick: (AppInfo) -> Unit
) {
    var selectedFilter by remember { mutableStateOf(AppFilter.ALL) }

    val visibleApps = apps.filter { app -> selectedFilter.matches(app) }

    Column(modifier = Modifier.fillMaxWidth()) {
        AppsHeader(
            appCount = apps.size,
            lastScanTime = lastScanTime,
            isScanning = isScanning,
            onRescanClick = onRescanClick
        )

        FilterChipsRow(
            apps = apps,
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        ScanInfoNote()

        if (apps.isEmpty()) {
            Text(
                text = "No apps with sensitive permissions were found.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(visibleApps) { app ->
                AppListItem(app = app, onClick = { onAppClick(app) })
            }
        }
    }
}


@Composable
private fun AppsHeader(
    appCount: Int,
    lastScanTime: Long?,
    isScanning: Boolean,
    onRescanClick: () -> Unit
) {
    val countText = if (appCount == 1) "1 app" else "$appCount apps"

    val timeFormat = android.text.format.DateFormat.getTimeFormat(LocalContext.current)
    val subtitle = if (lastScanTime != null) {
        "$countText · Last scanned ${timeFormat.format(lastScanTime)}"
    } else {
        countText
    }

    val purple = MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Installed Apps",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        OutlinedButton(
            onClick = onRescanClick,
            // Disabled while scanning so a second scan can't start on top of the first.
            enabled = !isScanning,
            border = BorderStroke(1.dp, purple),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = purple
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
            modifier = Modifier.height(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isScanning) "Scanning..." else "Rescan",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun ScanInfoNote() {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Only downloaded apps are analysed. System apps are excluded.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppsScreenEmptyPreview() {
    PrivacyAwarenessTheme {
        AppsScreen(
            apps = null,
            lastScanTime = null,
            isScanning = false,
            onScanClick = {},
            onAppClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppsScreenScannedPreview() {
    PrivacyAwarenessTheme {
        AppsScreen(
            apps = emptyList(),
            lastScanTime = System.currentTimeMillis(),
            isScanning = false,
            onScanClick = {},
            onAppClick = {}
        )
    }
}
