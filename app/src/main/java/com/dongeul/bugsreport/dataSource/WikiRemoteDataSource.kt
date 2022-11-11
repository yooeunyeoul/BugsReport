package com.dongeul.bugsreport.dataSource

import android.util.Log
import com.dongeul.bugsreport.model.BodyItem
import com.dongeul.bugsreport.model.HeaderItem
import com.dongeul.httpconnection.ConnectionType
import com.dongeul.httpconnection.HttpConnection
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection

class WikiRemoteDataSource {


    suspend fun getHeader(keyword: String): HeaderItem {
        val response = HttpConnection(
            address = "https://en.wikipedia.org/api/rest_v1/page/summary",
            method = ConnectionType.GET,
            keyword = keyword,
            timeout = 10000,
            headers = listOf(
                Pair(first = "Content-Type", "application/json"),
                Pair(first = "Accept", second = "application/json")
            )
        ).requestHttpConnection()
        return if (response.code == HttpURLConnection.HTTP_OK) {
            val jsonObject = JSONObject(response.data)
            val displayTitle = jsonObject.getString("displaytitle")
            val extra = jsonObject.getString("extract")
            var image = ""
            if (jsonObject.has("thumbnail")) {
                var thumbnail = jsonObject.getString("thumbnail") ?: ""
                val thumbnailObject = JSONObject(thumbnail)
                image = thumbnailObject.getString("source")
            }
            Log.d("return", response.toString())
            HeaderItem(displayTitle = displayTitle, extract = extra, thumbnail = image)
        } else {
            HeaderItem(displayTitle = "결과없음", extract = "결과없음", thumbnail = "")
        }

    }

    suspend fun getBody(keyword: String): List<BodyItem> {
        val response = HttpConnection(
            address = "https://en.wikipedia.org/api/rest_v1/page/related",
            method = ConnectionType.GET,
            keyword = keyword,
            timeout = 10000,
            headers = listOf(
                Pair(first = "Content-Type", "application/json"),
                Pair(first = "Accept", second = "application/json")
            )
        ).requestHttpConnection()
        if (response.code == HttpURLConnection.HTTP_OK) {
            val jsonObject = JSONObject(response.data)
            val pages = jsonObject.get("pages") as JSONArray

            val list = arrayListOf<BodyItem>()

            for (i in 0..pages.length() - 1) {
                val jsonObject = pages.get(i) as JSONObject
                val displaytitle = jsonObject.getString("displaytitle")
                val extract = jsonObject.getString("extract")
                var image = ""
                if (jsonObject.has("thumbnail")) {
                    var thumbnail = jsonObject.getString("thumbnail") ?: ""
                    val thumbnailObject = JSONObject(thumbnail)
                    image = thumbnailObject.getString("source")
                }
                list.add(
                    BodyItem(
                        displayTitle = displaytitle,
                        extract = extract,
                        thumbnail = image
                    )
                )
            }
            return list
        } else {
            return listOf<BodyItem>()
        }

    }


}