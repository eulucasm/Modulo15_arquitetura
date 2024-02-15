package com.lucao.hqawasomeapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucao.hqawasomeapp.data.Image

@Dao
interface ImageDao: BaseDao<Image> {

    @Query("SELECT * FROM image")
    suspend fun getAllImages(): List<Image>?

    @Query("SELECT * FROM image WHERE imageId=:imageId")
    suspend fun getImages(imageId: Int): Image?

}