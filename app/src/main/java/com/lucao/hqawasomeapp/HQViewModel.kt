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
import java.lang.Exception
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HQViewModel(
    application: Application
) : AndroidViewModel(
    application
) {

    private val comicDataBase = ComicsDataBase.getDataBase(application)
    private val comicDao = comicDataBase.comicDao(comicDataBase)

    val hqDetailsLiveData: LiveData<Comic>
        get() = _hqDetailsLiveData
    private val _hqDetailsLiveData = MutableLiveData<Comic>()

    val hqListLiveData: LiveData<List<Comic>?>
        get() = _hqListLiveData
    private val _hqListLiveData = MutableLiveData<List<Comic>?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Event<Unit>>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials.baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val comicService = retrofit.create(ComicService::class.java)

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
        val timestamp = ApiHelper.getCurrentTimesTemp()
        val input = "$timestamp${ApiCredentials.privateKey}${ApiCredentials.publicKey}"
        val hash = ApiHelper.generateMD5Hash(input)

        viewModelScope.launch {
            try {
                val response =
                    comicService.getComicsList(timestamp, ApiCredentials.publicKey, hash, 50)

                if (response.isSuccessful) {
                    val comics = response.body()?.data?.results
                    comics?.let {
                        persistComicData(it)
                    }
                    _hqListLiveData.postValue(comics)
                    _appState.postValue(DataState.SUCCESS)
                } else {
                    errorHandling()
                }
            } catch (e: Exception) {
                errorHandling()
            }
        }
    }

    private suspend fun errorHandling() {
        val comicList = loadPersistedComicData()

        if (comicList.isNullOrEmpty()) {
            _appState.postValue(DataState.ERROR)
        } else {
            _hqListLiveData.postValue(comicList)
            _appState.postValue(DataState.SUCCESS)
        }
    }

    private suspend fun persistComicData(comicList: List<Comic>) {
        comicDao.clearComicsData()
        comicDao.insertComicList(comicList)
    }

    private suspend fun loadPersistedComicData() = comicDao.getAllComics()?.map {
        mapComicWithPropertiesToComic(it)
    }

    private fun mapComicWithPropertiesToComic(comicWithAllProperties: ComicWithAllProperties): Comic {
        comicWithAllProperties.comic.images = comicWithAllProperties.images
        comicWithAllProperties.comic.textObject = comicWithAllProperties.textObject
        return comicWithAllProperties.comic
    }
}
