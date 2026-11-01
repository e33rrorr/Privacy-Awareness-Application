package com.example.privacyawareness.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.model.RiskLevel
import com.example.privacyawareness.ui.theme.Purple40

enum class AppFilter(
    val label: String,
    val backgroundColor: Color,
    val textColor: Color,
    val riskLevel: RiskLevel?
) {
    ALL(
        label = "All",
        backgroundColor = Purple40,
        textColor = Color.White,
        riskLevel = null
    ),
    HIGH_RISK(
        label = "High",
        backgroundColor = RiskLevel.HIGH.color.copy(alpha = 0.15f),
        textColor = RiskLevel.HIGH.color,
        riskLevel = RiskLevel.HIGH
    ),
    MEDIUM(
        label = "Medium",
        backgroundColor = RiskLevel.MEDIUM.color.copy(alpha = 0.15f),
        textColor = RiskLevel.MEDIUM.color,
        riskLevel = RiskLevel.MEDIUM
    ),
    LOW(
        label = "Low",
        backgroundColor = RiskLevel.LOW.color.copy(alpha = 0.15f),
        textColor = RiskLevel.LOW.color,
        riskLevel = RiskLevel.LOW
    );

    fun matches(app: AppInfo): Boolean {
        return riskLevel == null || app.riskLevel == riskLevel
    }
}


// Row of chips that filters the apps list by risk level.
@Composable
fun FilterChipsRow(
    apps: List<AppInfo>,
    selectedFilter: AppFilter,
    onFilterSelected: (AppFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (filter in AppFilter.entries) {
            val count = apps.count { app -> filter.matches(app) }
            val text = "${filter.label} ($count)"

            FilterChipItem(
                filter = filter,
                text = text,
                isSelected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                modifier = Modifier.weight(text.length.toFloat())
            )
        }
    }
}

@Composable
private fun FilterChipItem(
    filter: AppFilter,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(filter.backgroundColor)
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, filter.textColor, RoundedCornerShape(50))
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = text,
            style = TextStyle(
                color = filter.textColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(minFontSize = 10.sp, maxFontSize = 14.sp)
        )
    }
}


@Preview(showBackground = true, widthDp = 320, fontScale = 1.3f)
@Composable
private fun FilterChipsRowPreview() {
    FilterChipsRow(
        apps = emptyList(),
        selectedFilter = AppFilter.ALL,
        onFilterSelected = {}
    )
}
