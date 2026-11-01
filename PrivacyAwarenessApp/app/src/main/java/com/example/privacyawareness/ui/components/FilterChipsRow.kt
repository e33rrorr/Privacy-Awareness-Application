package com.example.privacyawareness.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.privacyawareness.ui.model.RiskLevel
import com.example.privacyawareness.ui.theme.Purple40

/**
 * The four filter options shown above the apps list.
 *
 * Counts are hardcoded placeholders for now. Once real filtering is wired up,
 * these can be computed from the actual app list instead.
 */
enum class AppFilter(
    val label: String,
    val count: Int,
    val backgroundColor: Color,
    val textColor: Color,
    // Which RiskLevel this chip matches against, or null for "All" (no filtering).
    val riskLevel: RiskLevel?
) {
    ALL(
        label = "All",
        count = 24,
        backgroundColor = Purple40,
        textColor = Color.White,
        riskLevel = null
    ),
    HIGH_RISK(
        label = "High Risk",
        count = 6,
        backgroundColor = RiskLevel.HIGH.color.copy(alpha = 0.15f),
        textColor = RiskLevel.HIGH.color,
        riskLevel = RiskLevel.HIGH
    ),
    MEDIUM(
        label = "Medium",
        count = 10,
        backgroundColor = RiskLevel.MEDIUM.color.copy(alpha = 0.15f),
        textColor = RiskLevel.MEDIUM.color,
        riskLevel = RiskLevel.MEDIUM
    ),
    LOW(
        label = "Low",
        count = 8,
        backgroundColor = RiskLevel.LOW.color.copy(alpha = 0.15f),
        textColor = RiskLevel.LOW.color,
        riskLevel = RiskLevel.LOW
    )
}

/**
 * Horizontally scrollable row of filter chips. Purely visual for now —
 * [selectedFilter] and [onFilterSelected] are owned by the caller so the
 * apps list can later react to the selection without changing this file.
 */
@Composable
fun FilterChipsRow(
    selectedFilter: AppFilter,
    onFilterSelected: (AppFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(AppFilter.entries) { filter ->
            FilterChipItem(
                filter = filter,
                isSelected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) }
            )
        }
    }
}

@Composable
private fun FilterChipItem(
    filter: AppFilter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "${filter.label} (${filter.count})",
            color = filter.textColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipsRowPreview() {
    FilterChipsRow(
        selectedFilter = AppFilter.ALL,
        onFilterSelected = {}
    )
}
