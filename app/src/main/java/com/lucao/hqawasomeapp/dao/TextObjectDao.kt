package com.lucao.hqawasomeapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.lucao.hqawasomeapp.data.TextObject

@Dao
interface TextObjectDao : BaseDao<TextObject> {

    @Query("SELECT * FROM textObject")
    suspend fun getAllText(): List<TextObject>?

    @Query("SELECT * FROM TextObject WHERE textObjectId=:textObjectId")
    suspend fun getText(textObjectId: Int): TextObject?
}