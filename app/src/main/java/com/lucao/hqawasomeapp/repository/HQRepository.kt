package com.lucao.hqawasomeapp.repository

import android.content.Context
import com.lucao.hqawasomeapp.data.Comic
import com.lucao.hqawasomeapp.dataSource.HQApiClientDataSource
import com.lucao.hqawasomeapp.dataSource.HQDataBaseDataSource

class HQRepository(context: Context) {
    private val hqApiClientDataSource = HQApiClientDataSource()
    private val hqDataBaseDataSource = HQDataBaseDataSource(context)


    suspend fun getHqData(): Result<List<Comic>?> {
        return try {
            val result = hqApiClientDataSource.getHqData()

            if (result.isSuccess) {
                persistData(result.getOrNull())
                result
            } else {
                getLocalData()
            }

        } catch (e: Exception) {
            return getLocalData()
        }
    }

    private suspend fun persistData(comicList: List<Comic>?) {
        hqDataBaseDataSource.clearData()
        comicList?.let {
            hqDataBaseDataSource.saveData(it)
        }
    }

    private suspend fun getLocalData(): Result<List<Comic>?> = hqDataBaseDataSource.getHqData()
}
