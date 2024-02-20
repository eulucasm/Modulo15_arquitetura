package com.lucao.hqawasomeapp.dataSource

import android.content.Context
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.data.ComicWithAllProperties
import com.lucao.hqawasomeapp.database.ComicsDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HQDataBaseDataSource(context: Context) : HQDataSource {

    private val comicsDataBase = ComicsDataBase.getDataBase(context)
    private val comicDao = comicsDataBase.comicDao(comicsDataBase)

    override suspend fun getHqData(): Result<List<Comic>?> =
        withContext(Dispatchers.IO) {
            Result.success(loadPersistedComicData())
        }

    override suspend fun saveData(comicList: List<Comic>) {
        comicDao.insertComicList(comicList)
    }

    override suspend fun clearData() {
        comicDao.clearComicsData()
    }

    private suspend fun loadPersistedComicData() = comicDao.getAllComics()?.map {
        mapComicWithPropertiesToComic(it)
    }

    private fun mapComicWithPropertiesToComic(comicWithAllProperties: ComicWithAllProperties): Comic {
        comicWithAllProperties.comic.images = comicWithAllProperties.images
        comicWithAllProperties.comic.textObject = comicWithAllProperties.textObject
        return comicWithAllProperties.comic
    }
}
