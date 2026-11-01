package com.example.privacyawareness.data
import com.example.privacyawareness.model.AppInfo
import org.json.JSONArray
import java.io.File


// Saves an app's permissions to a JSON file and returns that file.
fun writePermissionsJson(app: AppInfo, folder: File): File {

    folder.mkdirs()

    val file = File(folder, "${app.packageName}.json")
    file.writeText(permissionsToJson(app))

    return file
}


private fun permissionsToJson(app: AppInfo): String {

    val jsonArray = JSONArray()

    app.permissions.forEach { permission ->
        jsonArray.put(permission.name)
    }

    return jsonArray.toString(2)
}
