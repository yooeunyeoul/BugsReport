package com.dongeul.bugsreport.viewmodel

import android.preference.PreferenceActivity.Header
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongeul.bugsreport.dataSource.WikiRemoteDataSource
import com.dongeul.bugsreport.model.BodyItem
import com.dongeul.bugsreport.model.HeaderItem
import kotlinx.coroutines.launch

class MainViewModel(val dataSource: WikiRemoteDataSource) : ViewModel() {

    private val _headerItem = MutableLiveData<HeaderItem>()
    val headerItem: LiveData<HeaderItem> = _headerItem
    private val _bodyItem = MutableLiveData<List<BodyItem>>()
    val bodyItem: LiveData<List<BodyItem>> = _bodyItem

    fun getHeader(keyword: String) {
        viewModelScope.launch {
            _headerItem.postValue(dataSource.getHeader(keyword))
        }
    }

    fun getBody(keyword: String) {
        viewModelScope.launch {
            _bodyItem.postValue(dataSource.getBody(keyword))
        }
    }


}