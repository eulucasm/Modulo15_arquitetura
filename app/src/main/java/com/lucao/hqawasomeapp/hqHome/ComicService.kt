package com.lucao.hqawasomeapp.hqHome

import com.lucao.hqawasomeapp.data.ComicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicService {

    @GET("v1/public/comics")
    fun getComicsList(
        @Query("ts") timeStamp: String,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int
    ): Call<ComicResponse>
}