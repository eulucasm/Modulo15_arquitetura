package com.lucao.hqawasomeapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@Entity
@JsonClass(generateAdapter = true)
class Comic() {
    @PrimaryKey
    var id: Int? = null
    var title: String? = null
    var description: String? = null
    @Ignore
    var textObject: List<TextObject>? = null
    @Embedded
    var thumbnail: Image? = null
    @Ignore
    var images: List<Image>? = null

    constructor(
        id: Int?,
        title: String?,
        description: String?,
        textObject: List<TextObject>?,
        thumbnail: Image?,
        images: List<Image>?
    ) : this() {
        this.id = id
        this.title = title
        this.description = description
        this.textObject = textObject
        this.thumbnail = thumbnail
        this.images = images
    }

    fun getContent(): String {
        val desc = description
        val text = textObject
        return when {
            description?.isNotEmpty() == true -> desc.toString()
            textObject?.isNotEmpty() == true -> text!![0].text ?: "Conteudo não disponivel no momento"
            else -> "Conteudo não disponivel no momento"
        }
    }

    fun getIdString() = id?.toString() ?: ""

    fun getImageUrl() = thumbnail?.getFullImagePath()

    fun getCarouselImage(): List<CarouselItem>? = images?.map {
        CarouselItem(imageUrl = it.getFullImagePath())
    }

}
