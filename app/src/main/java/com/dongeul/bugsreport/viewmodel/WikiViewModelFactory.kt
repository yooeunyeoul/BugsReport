package com.dongeul.bugsreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dongeul.bugsreport.dataSource.WikiRemoteDataSource

class WikiViewModelFactory(private val dataSource: WikiRemoteDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}