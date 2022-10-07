package com.example.testthread.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object Utils {

    /**
     * 下载图片的网络请求
     *
     * @param urlString
     * @return
     */
     fun downloadUrlBitmap(urlString: String): Bitmap? {
        var urlConnection: HttpURLConnection? = null
        var `in`: BufferedInputStream? = null
        var bitmap: Bitmap? = null
        try {
            val url = URL(urlString)
            urlConnection = url.openConnection() as HttpURLConnection
            `in` = BufferedInputStream(urlConnection.inputStream, 8 * 1024)
            bitmap = BitmapFactory.decodeStream(`in`)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
            try {
                `in`?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bitmap
    }
}