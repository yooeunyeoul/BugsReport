package com.dongeul.bugsreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dongeul.httpconnection.ConnectionType
import com.dongeul.httpconnection.HttpConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HttpConnection(
            address = "https://en.wikipedia.org/api/rest_v1/page/summary",
            method = ConnectionType.GET,
            keyword = "type"
        )

    }
}