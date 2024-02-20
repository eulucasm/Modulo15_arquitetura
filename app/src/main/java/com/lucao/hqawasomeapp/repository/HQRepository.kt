package com.lucao.hqawasomeapp.repository

import androidx.lifecycle.viewModelScope
import com.lucao.hqawasomeapp.api.ComicService
import com.lucao.hqawasomeapp.data.ApiCredentials
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.data.ComicWithAllProperties
import com.lucao.hqawasomeapp.data.DataState
import com.lucao.hqawasomeapp.database.ComicsDataBase
import com.lucao.hqawasomeapp.helpers.ApiHelper
import java.lang.Exception
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HQRepository {
    suspend fun getHqData(): Result<List<Comic>> {
        return Result.success(emptyList())
    }
}

//private val retrofit = Retrofit.Builder()
//    .baseUrl(ApiCredentials.baseUrl)
//    .addConverterFactory(MoshiConverterFactory.create())
//    .build()
//
//private val comicService = retrofit.create(ComicService::class.java)
//
//private val comicsDataBase = ComicsDataBase.getDataBase(application)
//private val comicDao = comicsDataBase.comicDao(comicsDataBase)
//private fun getHqData() {
//    val timestamp = ApiHelper.getCurrentTimesTemp()
//    val input = "$timestamp${ApiCredentials.privateKey}${ApiCredentials.publicKey}"
//    val hash = ApiHelper.generateMD5Hash(input)
//
//    viewModelScope.launch {
//        try {
//            val response =
//                comicService.getComicsList(timestamp, ApiCredentials.publicKey, hash, 50)
//
//            if (response.isSuccessful) {
//                val comics = response.body()?.data?.results
//                comics?.let {
//                    persistComicData(it)
//                }
//                _hqListLiveData.postValue(comics)
//                _appState.postValue(DataState.SUCCESS)
//            } else {
//                errorHandling()
//            }
//        } catch (e: Exception) {
//            errorHandling()
//        }
//    }
//}
//
//private suspend fun errorHandling() {
//    val comicList = loadPersistedComicData()
//
//    if (comicList.isNullOrEmpty()) {
//        _appState.postValue(DataState.ERROR)
//    } else {
//        _hqListLiveData.postValue(comicList)
//        _appState.postValue(DataState.SUCCESS)
//    }
//}
//
//private suspend fun persistComicData(comicList: List<Comic>) {
//    comicDao.clearComicsData()
//    comicDao.insertComicList(comicList)
//}
//
//private suspend fun loadPersistedComicData() = comicDao.getAllComics()?.map {
//    mapComicWithPropertiesToComic(it)
//}
//
//private fun mapComicWithPropertiesToComic(comicWithAllProperties: ComicWithAllProperties): Comic {
//    comicWithAllProperties.comic.images = comicWithAllProperties.images
//    comicWithAllProperties.comic.textObject = comicWithAllProperties.textObject
//    return comicWithAllProperties.comic
//}