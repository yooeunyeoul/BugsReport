package com.dongeul.httpconnection

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

enum class ConnectionType {
    POST, GET
}

class HttpConnection {
    lateinit var url: URL
    var timeout: Int = 15000
    fun toast(context: Context) {
        Toast.makeText(context, "My Id : BESG,", Toast.LENGTH_SHORT).show()
    }

    constructor(
        address: String = "",
        timeout: Int = 15000,
        method: ConnectionType,
        keyword: String
    ) {
        Log.d("ADDRES", "${address}${keyword}")
        url = URL("${address}/${keyword}")
        this@HttpConnection.timeout = timeout

        when (method) {
            ConnectionType.GET -> {
                getMethod()
            }
            else -> {

            }
        }
    }

    private fun getMethod() {
        GlobalScope.launch(Dispatchers.IO) {
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.setRequestProperty(
                "Accept",
                "application/json"
            ) // The format of response we want to get from the server
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false

            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(JsonParser.parseString(response))
//                    Log.d("Pretty Printed JSON :", prettyJson)

                    Log.d("Pretty Printed JSON :", response)


                }
            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }

    }
}