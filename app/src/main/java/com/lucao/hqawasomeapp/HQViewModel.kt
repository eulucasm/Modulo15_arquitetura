package com.lucao.hqawasomeapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.data.DataState
import com.lucao.hqawasomeapp.api.ComicService
import com.lucao.hqawasomeapp.data.ApiCredentials
import com.lucao.hqawasomeapp.data.ComicWithAllProperties
import com.lucao.hqawasomeapp.data.Event
import com.lucao.hqawasomeapp.database.ComicsDataBase
import com.lucao.hqawasomeapp.helpers.ApiHelper
import com.lucao.hqawasomeapp.repository.HQRepository
import java.lang.Exception
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HQViewModel(application: Application) : AndroidViewModel(application) {

    val hqDetailsLiveData: LiveData<Comic> get() = _hqDetailsLiveData
    private val _hqDetailsLiveData = MutableLiveData<Comic>()

    val hqListLiveData: LiveData<List<Comic>?> get() = _hqListLiveData
    private val _hqListLiveData = MutableLiveData<List<Comic>?>()

    val appState: LiveData<DataState> get() = _appState
    private val _appState = MutableLiveData<DataState>()

    val navigationToDetailLiveData get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Event<Unit>>()

    private val hqRepository = HQRepository()

    init {
        _appState.postValue(DataState.LOADING)
        getHqData()
    }

    fun onHQSelected(position: Int) {
        val hqDetails = _hqListLiveData.value?.get(position)
        hqDetails.let {
            _hqDetailsLiveData.postValue(it)
            _navigationToDetailLiveData.postValue(Event(Unit))
        }
    }

    private fun getHqData() {
        viewModelScope.launch {
            val comicListResult = hqRepository.getHqData()

            comicListResult.fold(
                onSuccess = {
                    _hqListLiveData.value = it
                    _appState.value = DataState.SUCCESS
                },
                onFailure = {
                    _appState.value = DataState.ERROR
                }
            )
        }
    }
}
