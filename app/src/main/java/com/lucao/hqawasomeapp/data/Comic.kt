package com.lucao.hqawasomeapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comic(
    val id: Int?,
    val title: String?,
    val description: String?,
    val textObject: List<TextObject>?,
    val thumbnail: Image?,
) {
    fun getContent(): String {
        return when {
            description?.isNotEmpty() == true -> description
            textObject?.isNotEmpty() == true -> textObject[0].text
                ?: "Conteudo não disponivel no momento"

            else -> "Conteudo não disponivel no momento"
        }
    }

    fun getIdString(): String {
        return id?.toString() ?: ""
    }

    fun getImageUrl() = thumbnail?.getFullImagePath()

}
