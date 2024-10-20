package com.artushock.apps.spillme.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import org.joda.time.DateTime
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ImageSaveUtil(
    private val context: Context
) {


    fun createUriForPhoto(): Uri? {
        val storageDir: File = getAppImageDirectory()
        val imageFile = File.createTempFile("tempPicture_${DateTime.now().millis}", ".jpg", storageDir)
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
    }

    fun saveImageToInternalStorage(uri: Uri, formGallery: Boolean = false): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = "SpillMe${
                if (formGallery) "Picture"
                else "Photo"
            }_${DateTime.now().millis}.jpg"
            val storageDir: File = getAppImageDirectory()
            val file = File(storageDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getAppImageDirectory(): File {
        val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Images")

        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        return storageDir
    }
}