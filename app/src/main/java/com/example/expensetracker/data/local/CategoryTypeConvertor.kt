package com.example.expensetracker.data.local

import androidx.room.TypeConverter
import com.example.expensetracker.domain.models.Category

class CategoryTypeConvertor {
    @TypeConverter
    fun toCategory(value: String): Category {
        return enumValueOf<Category>(value)
    }

    @TypeConverter
    fun fromCategory(value: Category): String {
        return value.name
    }
}