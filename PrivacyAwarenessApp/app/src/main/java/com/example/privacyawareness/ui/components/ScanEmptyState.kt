package com.example.privacyawareness.ui.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.privacyawareness.ui.theme.PrivacyAwarenessTheme


// Shown before the first scan, with a button to start one.
@Composable
fun ScanEmptyState(
    isScanning: Boolean,
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScanAppsIllustration()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Scan for installed apps",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Find all installed apps on your device and check their permission risks.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onScanClick, enabled = !isScanning) {
            if (isScanning) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (isScanning) "Scanning..." else "Scan Apps")
        }
    }
}


@Composable
private fun ScanAppsIllustration(modifier: Modifier = Modifier) {
    val purple = MaterialTheme.colorScheme.primary
    val phoneColor = MaterialTheme.colorScheme.surface

    Canvas(modifier = modifier.size(160.dp)) {

        val w = size.width

        // Soft circle in the background.
        drawCircle(color = purple.copy(alpha = 0.08f), radius = w * 0.48f)

        // Phone body: filled first, then outlined.
        val phoneTopLeft = Offset(w * 0.28f, w * 0.14f)
        val phoneSize = Size(w * 0.38f, w * 0.68f)
        val phoneCorner = CornerRadius(w * 0.06f)
        drawRoundRect(
            color = phoneColor,
            topLeft = phoneTopLeft,
            size = phoneSize,
            cornerRadius = phoneCorner
        )
        drawRoundRect(
            color = purple.copy(alpha = 0.45f),
            topLeft = phoneTopLeft,
            size = phoneSize,
            cornerRadius = phoneCorner,
            style = Stroke(width = w * 0.02f)
        )

        // Speaker at the top of the phone.
        drawLine(
            color = purple.copy(alpha = 0.3f),
            start = Offset(w * 0.42f, w * 0.19f),
            end = Offset(w * 0.52f, w * 0.19f),
            strokeWidth = w * 0.02f,
            cap = StrokeCap.Round
        )

        // App list: four rows, each an icon square and a name bar.
        for (row in 0 until 4) {
            val y = w * (0.26f + row * 0.12f)
            drawRoundRect(
                color = purple.copy(alpha = 0.35f),
                topLeft = Offset(w * 0.33f, y),
                size = Size(w * 0.07f, w * 0.07f),
                cornerRadius = CornerRadius(w * 0.015f)
            )
            drawRoundRect(
                color = purple.copy(alpha = 0.18f),
                topLeft = Offset(w * 0.43f, y + w * 0.022f),
                size = Size(w * 0.18f, w * 0.026f),
                cornerRadius = CornerRadius(w * 0.013f)
            )
        }

        // Magnifying glass over the bottom-right of the phone.
        val lensCenter = Offset(w * 0.66f, w * 0.62f)
        val lensRadius = w * 0.13f
        // Covers the list rows behind the lens, then tints it purple.
        drawCircle(color = phoneColor, radius = lensRadius, center = lensCenter)
        drawCircle(color = purple.copy(alpha = 0.15f), radius = lensRadius, center = lensCenter)
        drawCircle(
            color = purple.copy(alpha = 0.7f),
            radius = lensRadius,
            center = lensCenter,
            style = Stroke(width = w * 0.035f)
        )

        // Handle, pointing down-right at 45 degrees (0.707 = cos 45°).
        val diagonal = 0.707f
        val handleLength = w * 0.14f
        drawLine(
            color = purple.copy(alpha = 0.7f),
            start = Offset(
                lensCenter.x + lensRadius * diagonal,
                lensCenter.y + lensRadius * diagonal
            ),
            end = Offset(
                lensCenter.x + (lensRadius + handleLength) * diagonal,
                lensCenter.y + (lensRadius + handleLength) * diagonal
            ),
            strokeWidth = w * 0.05f,
            cap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanEmptyStatePreview() {
    PrivacyAwarenessTheme {
        ScanEmptyState(isScanning = false, onScanClick = {})
    }
}
