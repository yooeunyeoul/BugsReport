@file:OptIn(DelicateCoroutinesApi::class)

package com.dongeul.httpconnection

import android.util.Log
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

enum class ConnectionType {
    POST, GET, PUT, DELETE
}

class HttpConnection(
    address: String = "",
    timeout: Int = 15000,
    method: ConnectionType,
    keyword: String = "default",
    headers: List<Pair<String, String>>,
    jsonObject: JSONObject = JSONObject()
) {
    private var mJsonObject: JSONObject = jsonObject
    var mUrl: URL
    var mTimeout: Int = timeout
    lateinit var mAddress: String
    var mMethod: ConnectionType = method
    var mKeyword: String = keyword
    var mHeaders: List<Pair<String, String>> = headers

    init {
        mUrl = URL("${address}/${keyword}")
        requestHttpConnection()
    }

    private fun requestHttpConnection() {
        GlobalScope.launch(Dispatchers.IO) {
            val httpURLConnection = mUrl.openConnection() as HttpURLConnection
            httpURLConnection.run {
                requestMethod = mMethod.name
                doInput = true
                doOutput = false
            }
            repeat(mHeaders.size) {
                httpURLConnection.setRequestProperty(
                    mHeaders[it].first,
                    mHeaders[it].second
                )
            }

            if (mMethod == ConnectionType.POST || mMethod == ConnectionType.PUT) {
                val jsonObjectString = mJsonObject.toString()
                val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
                outputStreamWriter.write(jsonObjectString)
                outputStreamWriter.flush()
            }

            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {
                    val jsonObject = JSONObject(response)
                    Log.d("Pretty Printed JSON :", jsonObject.toString())
                    val title = jsonObject.getString("title")
                    val type = jsonObject.getString("type")

                    Log.d("title", title)
                    Log.d("type", type)


                }
            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }
    }
}