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

data class ReturnStatus(val code: Int, val data: String)

class HttpConnection(
    val address: String = "",
    val timeout: Int = 15000,
    val method: ConnectionType,
    val keyword: String,
    val headers: List<Pair<String, String>>,
    val jsonObject: JSONObject = JSONObject()
) {
    lateinit var mUrl: URL
    private lateinit var returnStatus: ReturnStatus

    suspend fun requestHttpConnection(): ReturnStatus {
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            mUrl = URL("${address}/${keyword}")
            Log.d("URL","${address}/${keyword}")
            val httpURLConnection = mUrl.openConnection() as HttpURLConnection
            httpURLConnection.run {
                requestMethod = method.name
                doInput = true
                doOutput = false
                connectTimeout = timeout

            }
            repeat(headers.size) {
                httpURLConnection.setRequestProperty(
                    headers[it].first,
                    headers[it].second
                )
            }

            if (method == ConnectionType.POST || method == ConnectionType.PUT) {
                val jsonObjectString = jsonObject.toString()
                val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
                outputStreamWriter.write(jsonObjectString)
                outputStreamWriter.flush()
            }

            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {
                    returnStatus = ReturnStatus(code = HttpURLConnection.HTTP_OK, data = response)
                }
            } else {
                returnStatus = ReturnStatus(code = responseCode, data = "")
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }

        }
        waitFor.await()
        return returnStatus
    }
}