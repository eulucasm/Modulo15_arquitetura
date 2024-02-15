package com.lucao.hqawasomeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucao.hqawasomeapp.dao.ComicDao
import com.lucao.hqawasomeapp.dao.ImageDao
import com.lucao.hqawasomeapp.dao.TextObjectDao
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.data.Image
import com.lucao.hqawasomeapp.data.TextObject

@Database(
    entities = [
        Comic::class,
        Image::class,
        TextObject::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ComicsDataBase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun textObjectDao(): TextObjectDao
    abstract fun comicDao(comicsDataBase: ComicsDataBase): ComicDao

    companion object {
        @Volatile
        private var instance: ComicsDataBase? = null

        fun getDataBase(context: Context): ComicsDataBase {
            return instance ?: synchronized(this) {
                val dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    ComicsDataBase::class.java,
                    "comic_data_base"
                ).build()
                this.instance = dataBase
                return dataBase
            }
        }
    }
}