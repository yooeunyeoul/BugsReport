package com.dongeul.bugsreport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dongeul.bugsreport.adapter.BodyAdapter
import com.dongeul.bugsreport.dataSource.WikiRemoteDataSource
import com.dongeul.bugsreport.viewmodel.MainViewModel
import com.dongeul.bugsreport.viewmodel.WikiViewModelFactory
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    lateinit var adapter: BodyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)



        mainViewModel = ViewModelProvider(
            this,
            WikiViewModelFactory(WikiRemoteDataSource())
        )[MainViewModel::class.java]

        val keyword = intent.extras?.get("keyword").toString()

        mainViewModel.getHeader(keyword)
        mainViewModel.getBody(keyword)

        btnBack.setOnClickListener {
            onBackPressed()
        }

        with(mainViewModel) {
            headerItem.observe(this@SearchResultActivity) {
                Log.d(("item"), it.toString())
                tvKeyword.text = keyword
                headerTitle.text = it.displayTitle
                headerDes.text = it.extract
                headerIamge.setImageResource(R.drawable.ic_launcher_foreground)
            }

            bodyItem.observe(this@SearchResultActivity) {
                Log.d(("bodyitem"), it.toString())
                adapter= BodyAdapter(it)
                listViewBody.adapter = adapter
            }
        }


    }
}


