package com.teamdagger.financetracker.data.models

import com.synapsisid.smartdeskandroombooking.util.DataMapper
import com.teamdagger.financetracker.data.datasources.local.tables.LogsTable
import com.teamdagger.financetracker.domain.entities.Logs

object LogsTableMapper : DataMapper<LogsTable, Logs> {
    override fun entityToModel(entity: LogsTable): Logs {
        return Logs(entity.id, entity.category, entity.desc, entity.date, entity.month, entity.year, entity.nominal, entity.userId)
    }

    override fun modelToEntity(model: Logs): LogsTable {
        return LogsTable(model.id, model.category, model.desc, model.date, model.month, model.year, model.nominal, model.userId)
    }
}