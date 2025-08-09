package com.example.expensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetracker.domain.models.Category

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val category: Category,
    val date: Long = System.currentTimeMillis(),
    val notes: String?,
    val receiptImagePath: String?,
    val isSynced: Boolean = false
)
