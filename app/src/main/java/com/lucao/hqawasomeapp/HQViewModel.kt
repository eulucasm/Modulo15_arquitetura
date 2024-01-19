package com.lucao.hqawasomeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.data.ComicResponse
import com.lucao.hqawasomeapp.data.DataState
import com.lucao.hqawasomeapp.hqDetails.HQDetails
import com.lucao.hqawasomeapp.hqHome.ComicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HQViewModel : ViewModel() {

    val hqDetailsLiveData: LiveData<HQDetails>
        get() = _hqDetailsLiveData
    private val _hqDetailsLiveData = MutableLiveData<HQDetails>()

    val hqListLiveData: LiveData<List<Comic>?>
        get() = _hqListLiveData
    private val _hqListLiveData = MutableLiveData<List<Comic>?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

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
        val hqDetails = HQDetails("Minha HQ", "Conteudo maior contendo texto")
        _hqDetailsLiveData.postValue(hqDetails)
        _navigationToDetailLiveData.postValue(Unit)
    }

    private fun getHqData() {
        val timestamp = ApiHelper.getCurrentTimesTemp()
        val input = "$timestamp${ApiCredentials.privateKey}${ApiCredentials.publicKey}"
        val hash = ApiHelper.generateMD5Hash(input)

        comicService.getComicsList(timestamp, ApiCredentials.publicKey, hash, 50)
            .enqueue(object : Callback<ComicResponse> {
                override fun onResponse(
                    call: Call<ComicResponse>,
                    response: Response<ComicResponse>
                ) {
                    if (response.isSuccessful) {
                        _hqListLiveData.postValue(response.body()?.data?.results)
                        _appState.postValue(DataState.SUCCESS)
                    } else {
                        _appState.postValue(DataState.ERROR)
                    }
                }

                override fun onFailure(call: Call<ComicResponse>, t: Throwable) {
                    _appState.postValue(DataState.ERROR)
                }
            })
    }
}