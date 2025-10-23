package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.data.database.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.toEntity
import com.calyrsoft.ucbp1.features.dollar.data.toModel
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

class DollarLocalDataSource(
    val dao: IDollarDao
) {
    suspend fun getList(): List<DollarModel> {
        return dao.getList().map {
            it.toModel()
        }
    }
    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun insertDollars(lists: List<DollarModel>) {
        val dollarEntity = lists.map { it.toEntity()}
        dao.insertDollars(dollarEntity)
    }

    suspend fun insert(dollar: DollarModel) {
        dao.insert(dollar.toEntity())

    }
}