package com.example.privacyawareness.data
import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.example.privacyawareness.model.AppInfo
import com.example.privacyawareness.model.PermissionInfo

private const val ICON_SIZE_PX = 96


// Gets the Play Store apps on the phone that use sensitive permissions.
fun loadInstalledApps(packageManager: PackageManager): List<AppInfo> {

    return getInstalledPackages(packageManager)
        .filter { pkg -> isUserApp(pkg) }
        .filter { pkg -> isFromPlayStore(packageManager, pkg.packageName) }
        .mapNotNull { pkg -> toAppInfo(packageManager, pkg) }
        // Only show apps with sensitive permissions.
        .filter { app -> app.permissions.isNotEmpty() }
        .sortedBy { it.name.lowercase() }
}


// Asks Android for every installed app and its permissions.
@SuppressLint("QueryPermissionsNeeded")
private fun getInstalledPackages(packageManager: PackageManager): List<PackageInfo> {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        packageManager.getInstalledPackages(
            PackageManager.PackageInfoFlags.of(
                PackageManager.GET_PERMISSIONS.toLong()
            )
        )

    } else {

        @Suppress("DEPRECATION")
        packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
    }
}


private fun isUserApp(pkg: PackageInfo): Boolean {

    val app = pkg.applicationInfo ?: return false

    return app.flags and ApplicationInfo.FLAG_SYSTEM == 0
}


// Checks if the app was installed through Google Play.
@Suppress("DEPRECATION")
private fun isFromPlayStore(packageManager: PackageManager, packageName: String): Boolean {

    val installer = try {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            packageManager.getInstallSourceInfo(packageName).installingPackageName
        } else {
            packageManager.getInstallerPackageName(packageName)
        }

    } catch (e: PackageManager.NameNotFoundException) {
        null
    } catch (e: IllegalArgumentException) {
        null
    }

    return installer == "com.android.vending"
}


private fun toAppInfo(packageManager: PackageManager, pkg: PackageInfo): AppInfo? {

    val app = pkg.applicationInfo ?: return null

    val permissions = readSensitivePermissions(packageManager, pkg)

    return AppInfo(
        name = app.loadLabel(packageManager).toString(),
        packageName = pkg.packageName,
        icon = app.loadIcon(packageManager)
            .toBitmap(width = ICON_SIZE_PX, height = ICON_SIZE_PX)
            .asImageBitmap(),
        permissions = permissions
    )
}


// Returns the sensitive permissions an app requests, granted ones first.
private fun readSensitivePermissions(
    packageManager: PackageManager,
    pkg: PackageInfo
): List<PermissionInfo> {

    val requestedSensitive = pkg.requestedPermissions
        ?.filter { it in sensitivePermissions }
        ?.toSet()
        ?: emptySet()

    val permissions = requestedSensitive.map { permissionName ->
        val granted = packageManager.checkPermission(
            permissionName,
            pkg.packageName
        ) == PackageManager.PERMISSION_GRANTED

        PermissionInfo(name = permissionName, granted = granted)
    }

    return permissions.sortedWith(
        compareByDescending<PermissionInfo> { it.granted }
            .thenBy { it.name }
    )
}
