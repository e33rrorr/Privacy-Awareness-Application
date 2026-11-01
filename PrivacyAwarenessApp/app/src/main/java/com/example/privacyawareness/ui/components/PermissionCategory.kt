package com.example.privacyawareness.ui.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Sms
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


enum class PermissionCategory(
    val key: String,
    val title: String,
    val icon: ImageVector,
    val color: Color
) {
    LOCATION("location", "Location", Icons.Default.LocationOn, Color(0xFFE53935)),
    CAMERA("camera", "Camera", Icons.Default.PhotoCamera, Color(0xFF7E57C2)),
    MICROPHONE("microphone", "Microphone", Icons.Default.Mic, Color(0xFFFB8C00)),
    CONTACTS("contacts", "Contacts", Icons.Default.Contacts, Color(0xFF43A047)),
    CALENDAR("calendar", "Calendar", Icons.Default.Event, Color(0xFF1E88E5)),
    CALL_LOGS("call_logs", "Call logs", Icons.Default.History, Color(0xFF00897B)),
    PHONE("phone", "Phone", Icons.Default.Phone, Color(0xFF3949AB)),
    SMS("sms", "Messages", Icons.Default.Sms, Color(0xFFF4511E)),
    MEDIA("media", "Photos and media", Icons.Default.PhotoLibrary, Color(0xFFD81B60)),
    NEARBY_DEVICES("nearby_devices", "Nearby devices", Icons.Default.Bluetooth, Color(0xFF039BE5)),
    SENSORS("sensors", "Body sensors", Icons.Default.MonitorHeart, Color(0xFF8E24AA)),
    HEALTH("health", "Health data", Icons.Default.Favorite, Color(0xFFE91E63)),


    OTHER("other", "Other", Icons.Default.Shield, Color(0xFF757575));

    companion object {

        // Finds the category for a key such as "location", or OTHER if there's no match.
        fun fromKey(key: String?): PermissionCategory {
            return entries.find { it.key == key } ?: OTHER
        }
    }
}
