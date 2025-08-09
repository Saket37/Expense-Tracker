package com.example.expensetracker.domain.models

import com.example.expensetracker.data.local.entity.Expense

data class ExpenseListData(
    val expenses: List<Expense>,
    val totalAmount: Double
)
