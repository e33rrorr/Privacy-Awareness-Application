package com.example.privacyawareness.ui.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.model.PermissionDetail


// Tab that lists the app's privacy concerns, one per permission category.
@Composable
fun InsightsSummaryTab(
    app: AppInfo,
    permissionDetails: Map<String, PermissionDetail>
) {
    // Groups the app's permissions by category, keeping each permission's description.
    val descriptionsByCategory = app.permissions
        .groupBy(
            keySelector = { permission ->
                PermissionCategory.fromKey(permissionDetails[permission.name]?.category)
            },
            valueTransform = { permission ->
                permissionDetails[permission.name]?.description
                    ?: permission.name.substringAfterLast(".")
            }
        )
        .toSortedMap()

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Key Privacy Concerns")

        InsightsCard {
            var isFirst = true
            for ((category, descriptions) in descriptionsByCategory) {
                if (!isFirst) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
                isFirst = false

                ConcernRow(category = category, text = joinDescriptions(descriptions))
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
private fun ConcernRow(category: PermissionCategory, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SoftIcon(icon = category.icon, color = category.color)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = category.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


private fun joinDescriptions(descriptions: List<String>): String {
    val parts = descriptions.mapIndexed { index, description ->
        if (index == 0) description else description.replaceFirstChar { it.lowercase() }
    }

    val sentence = if (parts.size == 1) {
        parts[0]
    } else {
        parts.dropLast(1).joinToString(", ") + " and " + parts.last()
    }

    return "$sentence."
}
