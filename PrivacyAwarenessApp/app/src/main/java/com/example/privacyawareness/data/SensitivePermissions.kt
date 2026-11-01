package com.example.privacyawareness.data


val sensitivePermissions = setOf(

    // Calendar
    "android.permission.READ_CALENDAR",
    "android.permission.WRITE_CALENDAR",

    // Camera
    "android.permission.CAMERA",

    // Call logs
    "android.permission.READ_CALL_LOG",
    "android.permission.WRITE_CALL_LOG",

    // Contacts
    "android.permission.READ_CONTACTS",
    "android.permission.WRITE_CONTACTS",

    // Location
    "android.permission.ACCESS_BACKGROUND_LOCATION",
    "android.permission.ACCESS_COARSE_LOCATION",
    "android.permission.ACCESS_FINE_LOCATION",

    // Media
    "android.permission.ACCESS_MEDIA_LOCATION",
    "android.permission.READ_MEDIA_AUDIO",
    "android.permission.READ_MEDIA_IMAGES",
    "android.permission.READ_MEDIA_VIDEO",
    "android.permission.READ_MEDIA_VISUAL_USER_SELECTED",

    // Microphone
    "android.permission.RECORD_AUDIO",

    // Nearby devices
    "android.permission.BLUETOOTH_CONNECT",
    "android.permission.BLUETOOTH_SCAN",

    // Phone
    "android.permission.CALL_PHONE",
    "android.permission.READ_PHONE_STATE",

    // Sensors
    "android.permission.BODY_SENSORS",

    // SMS
    "android.permission.READ_SMS",
    "android.permission.RECEIVE_SMS",
    "android.permission.SEND_SMS",

    // Health Connect
    "android.permission.health.READ_BLOOD_GLUCOSE",
    "android.permission.health.READ_BLOOD_PRESSURE",
    "android.permission.health.READ_HEART_RATE",
    "android.permission.health.READ_SLEEP",
    "android.permission.health.READ_STEPS",
    "android.permission.health.READ_WEIGHT"
)
