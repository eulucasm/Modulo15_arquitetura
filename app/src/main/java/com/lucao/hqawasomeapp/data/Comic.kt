package com.lucao.hqawasomeapp.data

import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@JsonClass(generateAdapter = true)
data class Comic(
    val id: Int?,
    val title: String?,
    val description: String?,
    val textObject: List<TextObject>?,
    val thumbnail: Image?,
    val images: List<Image>?
) {
    fun getContent(): String {
        return when {
            description?.isNotEmpty() == true -> description
            textObject?.isNotEmpty() == true -> textObject[0].text
                ?: "Conteudo não disponivel no momento"

            else -> "Conteudo não disponivel no momento"
        }
    }

    fun getIdString() = id?.toString() ?: ""

    fun getImageUrl() = thumbnail?.getFullImagePath()

    fun getCarouselImage():List<CarouselItem>? = images?.map {
        CarouselItem(imageUrl = it.getFullImagePath())
    }

}
