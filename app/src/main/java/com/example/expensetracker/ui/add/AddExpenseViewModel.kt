package com.example.expensetracker.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.domain.models.Category
import com.example.expensetracker.domain.repository.AddExpenseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration

data class ExpenseUiState(
    val title: String = "",
    val amount: String = "",
    val category: Category? = null,
    val notes: String = "",
    val receiptImagePath: String? = null,
    val isSaving: Boolean = false,
    val error: String? = null,
    val totalAmountSpent: Double = 0.0
)

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}

class AddExpenseViewModel(
    private val repository: AddExpenseRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTodayTotalExpense().collect { total ->
                _uiState.update { currentState ->
                    currentState.copy(totalAmountSpent = total)
                }
            }
        }
    }

    private val _eventChannel = Channel<UiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onEvent(event: AddExpenseEvent) {
        when (event) {
            is AddExpenseEvent.TitleChanged -> onTitleChange(event.title)
            is AddExpenseEvent.AmountChanged -> onAmountChange(event.amount)
            is AddExpenseEvent.CategoryChanged -> onCategoryChange(event.category)
            is AddExpenseEvent.NotesChanged -> onNotesChange(event.notes)
            AddExpenseEvent.SaveExpense -> {
                saveExpense()
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onAmountChange(newAmount: String) {
        val regex = Regex("^\\d*(\\.\\d{0,2})?$")
        if (newAmount.isEmpty() || newAmount.matches(regex)) {
            _uiState.update { it.copy(amount = newAmount) }
        }
    }

    fun onCategoryChange(newCategory: Category) {
        _uiState.update { it.copy(category = newCategory) }
    }

    fun onNotesChange(newNotes: String) {
        _uiState.update { it.copy(notes = newNotes) }
    }

    private fun saveExpense() {
        viewModelScope.launch {
            val currentState = _uiState.value

            // Validate input
            if (currentState.title.isBlank() || currentState.amount.isBlank() || currentState.category == null) {
                _eventChannel.send(UiEvent.ShowToast("Title, Amount, and Category are required."))
                return@launch
            }

            val amountAsDouble = currentState.amount.toDoubleOrNull()
            if (amountAsDouble == null || amountAsDouble <= 0) {
                _eventChannel.send(UiEvent.ShowToast("Please enter a valid amount."))
                return@launch
            }
            _uiState.update { it.copy(isSaving = true, error = null) }

            val newExpense = Expense(
                title = currentState.title,
                amount = amountAsDouble,
                category = currentState.category,
                notes = currentState.notes.takeIf { it.isNotBlank() },
                receiptImagePath = currentState.receiptImagePath
            )

            // Check for duplicates in last 1 hour
            val since = System.currentTimeMillis() - Duration.ofHours(1).toMillis()
            val isDuplicate = repository.isDuplicateExpense(newExpense, since)

            if (isDuplicate) {
                _uiState.update { it.copy(isSaving = false) }
                _eventChannel.send(UiEvent.ShowToast("This expense already exists."))
                return@launch
            }

            // Insert into DB
            repository.insertExpense(newExpense)

            _uiState.update { it.copy(isSaving = false) }
            _eventChannel.send(UiEvent.ShowToast("Expense saved successfully."))
        }
    }
}