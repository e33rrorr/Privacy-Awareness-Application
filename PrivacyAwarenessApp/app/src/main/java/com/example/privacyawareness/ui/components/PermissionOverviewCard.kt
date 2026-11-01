package com.example.privacyawareness.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.GppMaybe
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.model.RiskLevel


// Card that shows an app's permission count and risk level.
@Composable
fun PermissionOverviewCard(app: AppInfo) {
    InsightsCard {
        Text(
            text = "Permission Overview",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Risk level is based on the number of sensitive permission categories identified.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PermissionCountBox(
                count = app.permissions.size,
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
            )
            RiskBox(
                riskLevel = app.riskLevel,
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(12.dp))

        RiskInfoNote()
    }
}

@Composable
private fun PermissionCountBox(count: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$count",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (count == 1) "Permission" else "Permissions",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RiskBox(riskLevel: RiskLevel?, modifier: Modifier = Modifier) {
    val color = riskLevel?.color ?: MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
            .background(color.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = riskIcon(riskLevel),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = riskLevel?.label ?: "Not available",
                color = color,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = riskExplanation(riskLevel),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RiskInfoNote() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "Risk levels indicate potential privacy exposure, " +
                    "not whether an app is malicious.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


private fun riskIcon(riskLevel: RiskLevel?): ImageVector {
    return when (riskLevel) {
        RiskLevel.HIGH -> Icons.Default.GppMaybe
        RiskLevel.MEDIUM -> Icons.Default.Shield
        RiskLevel.LOW -> Icons.Default.GppGood
        null -> Icons.Default.Shield
    }
}

private fun riskExplanation(riskLevel: RiskLevel?): String {
    return when (riskLevel) {
        RiskLevel.HIGH ->
            "This app can access many types of personal data, which may increase privacy risks."
        RiskLevel.MEDIUM ->
            "This app requests permissions across several sensitive categories. " +
                    "Consider whether each permission is necessary."
        RiskLevel.LOW ->
            "This app can access only a few types of personal data."
        null ->
            "The risk level for this app is not available yet."
    }
}
