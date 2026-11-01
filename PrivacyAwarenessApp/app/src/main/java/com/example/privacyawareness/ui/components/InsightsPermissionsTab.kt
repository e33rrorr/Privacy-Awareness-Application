package com.example.privacyawareness.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.model.PermissionDetail
import com.example.privacyawareness.model.PermissionInfo

private val AllowedGreen = Color(0xFF2E7D32)
private val TipYellow = Color(0xFFF9A825)


// Tab that lists each permission and whether the user allowed it.
@Composable
fun InsightsPermissionsTab(
    app: AppInfo,
    permissionDetails: Map<String, PermissionDetail>
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Requested Permissions")

        InsightsCard {
            app.permissions.forEachIndexed { index, permission ->
                if (index > 0) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
                PermissionRow(
                    permission = permission,
                    detail = permissionDetails[permission.name]
                )
            }
        }
    }
}

@Composable
private fun PermissionRow(permission: PermissionInfo, detail: PermissionDetail?) {
    val category = PermissionCategory.fromKey(detail?.category)

    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SoftIcon(icon = category.icon, color = category.color)
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = readablePermissionName(permission.name),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = detail?.description ?: "No description available.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            AllowedPill(allowed = permission.granted)
        }

        val advice = detail?.whatToConsider
        if (advice != null) {
            Spacer(modifier = Modifier.height(12.dp))
            WhatToConsiderBox(advice)
        }
    }
}

@Composable
private fun WhatToConsiderBox(advice: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TipYellow.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Lightbulb,
            contentDescription = null,
            tint = TipYellow,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "What to consider",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = advice,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AllowedPill(allowed: Boolean) {
    val color = if (allowed) AllowedGreen else MaterialTheme.colorScheme.onSurfaceVariant

    Text(
        text = if (allowed) "Allowed" else "Not allowed",
        color = color,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}


private fun readablePermissionName(name: String): String {
    return name
        .substringAfterLast(".")
        .replace("_", " ")
        .lowercase()
        .replaceFirstChar { it.uppercase() }
}
