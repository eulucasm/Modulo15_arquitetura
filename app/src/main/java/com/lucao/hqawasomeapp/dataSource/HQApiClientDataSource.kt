package com.lucao.hqawasomeapp.dataSource

import com.lucao.hqawasomeapp.api.ComicService
import com.lucao.hqawasomeapp.data.ApiCredentials
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.helpers.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HQApiClientDataSource : HQDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials.baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val comicService = retrofit.create(ComicService::class.java)

    override suspend fun getHqData(): Result<List<Comic>?> =
        withContext(Dispatchers.IO) {

            val timestamp = ApiHelper.getCurrentTimesTemp()
            val input = "$timestamp${ApiCredentials.privateKey}${ApiCredentials.publicKey}"
            val hash = ApiHelper.generateMD5Hash(input)

            val response =
                comicService.getComicsList(timestamp, ApiCredentials.publicKey, hash, 100)

            when {
                response.isSuccessful -> Result.success(response.body()?.data?.results)
                else -> Result.failure(Throwable(response.message()))
            }
        }

    override suspend fun saveData(comicList: List<Comic>) {
        //NO-OP
    }

    override suspend fun clearData() {
        //NO-OP
    }
}