package com.calyrsoft.ucbp1.features.dollar.data

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

fun DollarEntity.toModel() : DollarModel {
    return DollarModel(
        oficial = dollarOfficial,
        paralelo = dollarParallel,
        usdt = dollarUsdt,
        usdc = dollarUsdc,
        timestamp = timestamp

    )
}
fun DollarModel.toEntity() : DollarEntity {
    val entity = DollarEntity()
    entity.dollarOfficial = oficial
    entity.dollarParallel = paralelo
    entity.dollarUsdc = usdc
    entity.dollarUsdt = usdt
    entity.timestamp = if(timestamp==0L) System.currentTimeMillis() else timestamp
    return entity
}