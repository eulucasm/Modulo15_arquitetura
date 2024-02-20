package com.lucao.hqawasomeapp.dataSource

import com.lucao.hqawasomeapp.data.Comic

interface HQDataSource {
    suspend fun getHqData(): Result<List<Comic>?>
    suspend fun saveData(comicList: List<Comic>)
    suspend fun clearData()
}