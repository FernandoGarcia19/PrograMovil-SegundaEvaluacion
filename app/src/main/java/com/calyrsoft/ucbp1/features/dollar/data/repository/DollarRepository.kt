package com.calyrsoft.ucbp1.features.dollar.data.repository

import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class DollarRepository(
    val realTimeDataSource: RealTimeRemoteDataSource,
    val localData: DollarLocalDataSource
): IDollarRepository {
    override suspend fun getDollar(): Flow<DollarModel> {
        return realTimeDataSource.getDollarUpdates()
            .onEach {
                localData.insert(it)
            }
    }
}