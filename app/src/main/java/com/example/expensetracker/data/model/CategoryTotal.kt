package com.example.expensetracker.data.model

import com.example.expensetracker.domain.models.Category

data class CategoryTotal(
    val category: Category,
    val total: Double
)
