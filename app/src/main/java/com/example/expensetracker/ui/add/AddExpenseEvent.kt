package com.example.expensetracker.ui.add

import com.example.expensetracker.domain.models.Category

sealed interface AddExpenseEvent {
    data class TitleChanged(val title: String) : AddExpenseEvent
    data class AmountChanged(val amount: String) : AddExpenseEvent
    data class CategoryChanged(val category: Category) : AddExpenseEvent
    data class NotesChanged(val notes: String) : AddExpenseEvent
    data object SaveExpense : AddExpenseEvent
}