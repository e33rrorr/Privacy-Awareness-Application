package com.example.privacyawareness.ui.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.privacyawareness.ui.navigation.Destination

// Bar at the bottom of the screen for switching between tabs.
@Composable
fun BottomNavBar(
    selectedDestination: Destination,
    onDestinationSelected: (Destination) -> Unit
) {

    // Shared colors so every tab turns blue when it's the selected one.
    val selectedColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Blue,
        selectedTextColor = Color.Blue,
        indicatorColor = Color.Transparent
    )

    NavigationBar {

        NavigationBarItem(
            selected = selectedDestination == Destination.Apps,
            onClick = { onDestinationSelected(Destination.Apps) },
            icon = {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "Apps"
                )
            },
            label = {
                Text("Apps")
            },
            colors = selectedColors
        )

        NavigationBarItem(
            selected = selectedDestination == Destination.Policies,
            onClick = { onDestinationSelected(Destination.Policies) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Policies"
                )
            },
            label = {
                Text("Policies")
            },
            colors = selectedColors
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(
        selectedDestination = Destination.Apps,
        onDestinationSelected = { }
    )
}