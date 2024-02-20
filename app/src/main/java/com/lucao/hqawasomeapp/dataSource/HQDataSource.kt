package com.lucao.hqawasomeapp.dataSource

import com.lucao.hqawasomeapp.data.Comic

interface HQDataSource {
    suspend fun getHqData() :Result<List<Comic>>
}