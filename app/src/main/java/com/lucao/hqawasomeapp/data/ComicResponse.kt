package com.lucao.hqawasomeapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicResponse(
    val code: Int?,
    val status: String?,
    val data: ComicContainer?
)
