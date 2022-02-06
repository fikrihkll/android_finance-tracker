package com.teamdagger.financetracker.features.data.models

import com.synapsisid.smartdeskandroombooking.util.DataMapper
import com.teamdagger.financetracker.features.domain.entities.LogsDetail

object LogsDetailMapper : DataMapper<LogsDetailModel, LogsDetail> {
    override fun entityToModel(entity: LogsDetailModel): LogsDetail {
        return LogsDetail(entity.category, entity.total)
    }

    override fun modelToEntity(model: LogsDetail): LogsDetailModel {
        return LogsDetailModel(model.category, model.total)
    }
}