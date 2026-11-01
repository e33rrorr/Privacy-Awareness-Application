package com.example.privacyawareness.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.model.PermissionDetail
import com.example.privacyawareness.ui.components.InsightsPermissionsTab
import com.example.privacyawareness.ui.components.InsightsSummaryTab
import com.example.privacyawareness.ui.components.PermissionOverviewCard

private enum class InsightsTab(val label: String) {
    SUMMARY("Summary"),
    PERMISSIONS("Permissions")
}

// Screen that shows one app's permissions and risk level.
@Composable
fun AppInsightsScreen(
    app: AppInfo,
    permissionDetails: Map<String, PermissionDetail>,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(InsightsTab.SUMMARY) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InsightsHeader(app = app, onBack = onBack)

        PermissionOverviewCard(app)
        PrimaryTabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = Color.Transparent
        ) {
            for (tab in InsightsTab.entries) {
                Tab(
                    selected = tab == selectedTab,
                    onClick = { selectedTab = tab },
                    text = { Text(tab.label) },
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        when (selectedTab) {
            InsightsTab.SUMMARY -> InsightsSummaryTab(app, permissionDetails)
            InsightsTab.PERMISSIONS -> InsightsPermissionsTab(app, permissionDetails)
        }
    }
}

@Composable
private fun InsightsHeader(app: AppInfo, onBack: () -> Unit) {
    val subtitle =
        if (app.permissions.size == 1) "1 permission" else "${app.permissions.size} permissions"

    Column {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                bitmap = app.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(18.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
