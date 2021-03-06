package com.teamdagger.financetracker.features.data.models

import com.synapsisid.smartdeskandroombooking.util.DataMapper
import com.teamdagger.financetracker.features.domain.entities.MonthExpense

object MonthExpenseMapper : DataMapper<MonthExpenseModel, MonthExpense> {
    override fun entityToModel(entity: MonthExpenseModel): MonthExpense {
        return MonthExpense(entity.total)
    }

    override fun modelToEntity(model: MonthExpense): MonthExpenseModel {
        return  MonthExpenseModel(model.total)
    }
}