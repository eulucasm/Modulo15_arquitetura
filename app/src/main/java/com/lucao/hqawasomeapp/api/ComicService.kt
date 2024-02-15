package com.lucao.hqawasomeapp.api

import com.lucao.hqawasomeapp.data.ComicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicService {

    @GET("v1/public/comics")
    suspend fun getComicsList(
        @Query("ts") timeStamp: String,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int
    ): Response<ComicResponse>
}