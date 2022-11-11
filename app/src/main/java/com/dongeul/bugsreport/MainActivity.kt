package com.dongeul.bugsreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.dongeul.bugsreport.dataSource.WikiRemoteDataSource
import com.dongeul.bugsreport.viewmodel.MainViewModel
import com.dongeul.bugsreport.viewmodel.WikiViewModelFactory
import com.dongeul.httpconnection.ConnectionType
import com.dongeul.httpconnection.HttpConnection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener {
            val keyword = edtSearch.text
            Intent(this, SearchResultActivity::class.java).run {
                putExtra("keyword", keyword)
                startActivity(this)
            }
        }


    }
}


