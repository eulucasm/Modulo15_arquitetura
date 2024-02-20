package com.lucao.hqawasomeapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.data.ComicWithAllProperties
import com.lucao.hqawasomeapp.database.ComicsDataBase

@Dao
abstract class ComicDao(
    comicsDataBase: ComicsDataBase
) : BaseDao<Comic> {

    private val imageDao = comicsDataBase.imageDao()
    private val textObjectDao = comicsDataBase.textObjectDao()

    @Transaction
    @Query("SELECT * FROM comic")
    abstract suspend fun getAllComics(): List<ComicWithAllProperties>?

    @Transaction
    @Query("SELECT * FROM comic WHERE id=:id")
    abstract suspend fun getComic(id: Int): ComicWithAllProperties?

    @Transaction
    @Query("DELETE from comic")
    abstract suspend fun clearComicsData()

    @Transaction
    open suspend fun insertComicList(comicList: List<Comic>) {
        comicList.forEach {
            insertComic(it)
        }
    }

    @Transaction
    open suspend fun insertComic(comic: Comic) {
        comic.thumbnail?.comicId = comic.id
        comic.images?.forEach {
            it.comicId = comic.id
        }
        comic.textObject?.forEach {
            it.comicId = comic.id
        }

        insert(comic)
        comic.textObject?.let {
            textObjectDao.insertList(it)
        }
        comic.images?.let {
            imageDao.insertList(it)
        }
    }
}