package com.dongeul.bugsreport.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object Util {
    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            val myBitmap: Bitmap = BitmapFactory.decodeStream(input)

            myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}