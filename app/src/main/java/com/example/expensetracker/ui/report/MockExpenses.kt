package com.example.expensetracker.ui.report

import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.domain.models.Category
import com.example.expensetracker.utils.Utility.getTimestampForDate

val mockExpenses = listOf(
    Expense(
        title = "Lunch with client",
        amount = 45.50,
        category = Category.FOOD,
        date = getTimestampForDate(2025, 8, 4),
        notes = "Client meeting",
        receiptImagePath = null
    ),
    Expense(
        title = "Uber to airport",
        amount = 32.00,
        category = Category.TRAVEL,
        date = getTimestampForDate(2025, 8, 4),
        notes = "Business trip",
        receiptImagePath = null
    ),

    Expense(
        title = "Office utilities",
        amount = 120.75,
        category = Category.UTILITY,
        date = getTimestampForDate(2025, 8, 5),
        notes = null,
        receiptImagePath = null
    ),
    Expense(
        title = "Team dinner",
        amount = 210.00,
        category = Category.FOOD,
        date = getTimestampForDate(2025, 8, 5),
        notes = "Project completion",
        receiptImagePath = null
    ),
    Expense(
        title = "Flight to NY",
        amount = 300.00,
        category = Category.TRAVEL,
        date = getTimestampForDate(2025, 8, 5),
        notes = null,
        receiptImagePath = null
    ),

    Expense(
        title = "Internet bill",
        amount = 90.00,
        category = Category.UTILITY,
        date = getTimestampForDate(2025, 8, 6),
        notes = null,
        receiptImagePath = null
    ),
    Expense(
        title = "Breakfast meeting",
        amount = 25.00,
        category = Category.FOOD,
        date = getTimestampForDate(2025, 8, 6),
        notes = "Team sync",
        receiptImagePath = null
    ),
    Expense(
        title = "Hotel stay",
        amount = 450.00,
        category = Category.TRAVEL,
        date = getTimestampForDate(2025, 8, 6),
        notes = "Conference",
        receiptImagePath = null
    ),

    Expense(
        title = "Staff bonus",
        amount = 1000.00,
        category = Category.STAFF,
        date = getTimestampForDate(2025, 8, 7),
        notes = "Performance reward",
        receiptImagePath = null
    ),
    Expense(
        title = "Client lunch",
        amount = 60.00,
        category = Category.FOOD,
        date = getTimestampForDate(2025, 8, 7),
        notes = null,
        receiptImagePath = null
    ),

    Expense(
        title = "Taxi to meeting",
        amount = 18.00,
        category = Category.TRAVEL,
        date = getTimestampForDate(2025, 8, 8),
        notes = null,
        receiptImagePath = null
    ),
    Expense(
        title = "Office snacks",
        amount = 35.00,
        category = Category.FOOD,
        date = getTimestampForDate(2025, 8, 8),
        notes = null,
        receiptImagePath = null
    ),
    Expense(
        title = "Cleaning services",
        amount = 80.00,
        category = Category.UTILITY,
        date = getTimestampForDate(2025, 8, 8),
        notes = "Monthly",
        receiptImagePath = null
    ),

    Expense(
        title = "Lunch",
        amount = 20.00,
        category = Category.FOOD,
        date = getTimestampForDate(2025, 8, 9),
        notes = null,
        receiptImagePath = null
    ),
    Expense(
        title = "Bus ticket",
        amount = 12.00,
        category = Category.TRAVEL,
        date = getTimestampForDate(2025, 8, 9),
        notes = "Local travel",
        receiptImagePath = null
    ),
    Expense(
        title = "Printer paper",
        amount = 25.00,
        category = Category.UTILITY,
        date = getTimestampForDate(2025, 8, 9),
        notes = null,
        receiptImagePath = null
    ),

    Expense(
        title = "Contractor fee",
        amount = 500.00,
        category = Category.STAFF,
        date = getTimestampForDate(2025, 8, 10),
        notes = "Freelancer payment",
        receiptImagePath = null
    ),
    Expense(
        title = "Dinner with team",
        amount = 150.00,
        category = Category.FOOD,
        date = getTimestampForDate(2025, 8, 10),
        notes = null,
        receiptImagePath = null
    ),
    Expense(
        title = "Water bill",
        amount = 45.00,
        category = Category.UTILITY,
        date = getTimestampForDate(2025, 8, 10),
        notes = null,
        receiptImagePath = null
    ),
    Expense(
        title = "Train to HQ",
        amount = 60.00,
        category = Category.TRAVEL,
        date = getTimestampForDate(2025, 8, 10),
        notes = null,
        receiptImagePath = null
    ),
)