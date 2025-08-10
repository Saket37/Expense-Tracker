package com.example.expensetracker.ui.add

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensetracker.designsystem.component.AppTextField
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.designsystem.theme.OffWhite
import com.example.expensetracker.domain.models.Category
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.util.Locale


// TODO add enter and exit anim and update UI and add image upload
@Composable
fun AddExpenseRoot(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseViewModel = koinViewModel(),
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    AddExpenses(
        uiState = uiState, onEvent = viewModel::onEvent, onClose = {
            onClose()
        })
}

@Composable
private fun AddExpenses(
    modifier: Modifier = Modifier,
    uiState: ExpenseUiState,
    onEvent: (AddExpenseEvent) -> Unit,
    onClose: () -> Unit
) {
    Scaffold(
        topBar = {
            AddExpenseTopAppBar(expense = uiState.totalAmountSpent, onClose = { onClose() })
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            RupeeInputField(
                amount = uiState.amount,
                onAmountChanged = { onEvent(AddExpenseEvent.AmountChanged(it)) })
            AppTextField(
                text = uiState.title,
                labelText = "Title",
                placeholderText = "Enter Title for Expense",
                singleLine = true,
                imeAction = ImeAction.Next,
                onTextChange = {
                    onEvent(AddExpenseEvent.TitleChanged(it))
                })
            CategoryChips(selectedCategory = uiState.category, onCategorySelected = {
                onEvent(AddExpenseEvent.CategoryChanged(it))
            })

            AppTextField(
                modifier = Modifier.height(180.dp),
                text = uiState.notes,
                labelText = "Notes",
                placeholderText = "Add Notes for Expense",
                singleLine = false,
                imeAction = ImeAction.Unspecified,
                maxLength = 100,
                showCharLimit = true,
                onTextChange = {
                    onEvent(AddExpenseEvent.NotesChanged(it))
                })

            Spacer(Modifier.weight(1f))
            ActionButtonsRow(onCancelClick = { onClose() }, onSaveClick = {
                onEvent(AddExpenseEvent.SaveExpense)
                onClose()
            })
        }

    }
}

@Composable
private fun AddExpenseTopAppBar(
    modifier: Modifier = Modifier, expense: Double, onClose: () -> Unit
) {
    val formatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    val formattedExpense = formatter.format(expense)
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary, contentColor = OffWhite
        ), shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .windowInsetsPadding(WindowInsets.statusBars),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onClose()
                        },
                    tint = OffWhite
                )
                Text(
                    "Add Expense",
                    style = LocalTypography.current.label,
                    fontSize = 24.sp,
                    color = OffWhite,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Total Spent Today : ",
                    style = LocalTypography.current.label,
                    fontSize = 18.sp,
                    color = OffWhite
                )
                Text(
                    formattedExpense, style = LocalTypography.current.headingLarge, fontSize = 16.sp
                )
            }
        }

    }
}


@Composable
fun RupeeInputField(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    textColor: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    leadingIconColor: Color = MaterialTheme.colorScheme.inverseSurface,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.surface,
    focusedBorderColor: Color = MaterialTheme.colorScheme.surface,
    amount: String,
    onAmountChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        value = amount,
        onValueChange = { newText ->
            val regex = Regex("^\\d*(\\.\\d{0,2})?$")
            if (newText.isEmpty() || newText.matches(regex)) {
                onAmountChanged(newText)
            }
        },
        leadingIcon = { Text("₹") },
        placeholder = { Text("100") },
        label = { Text("Amount") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = containerColor,
            focusedContainerColor = containerColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedLeadingIconColor = leadingIconColor,
            unfocusedLeadingIconColor = leadingIconColor
        ),
        shape = RoundedCornerShape(16.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CategoryChips(
    selectedCategory: Category?, onCategorySelected: (Category) -> Unit
) {
    val categories = Category.entries.toTypedArray()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            "Category",
            style = LocalTypography.current.label,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category.displayName) },
                    leadingIcon = if (category == selectedCategory) {
                        {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Selected",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                    shape = RoundedCornerShape(32.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        labelColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                        selectedLabelColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.surfaceContainerHighest
                    )


                )
            }
        }
    }
}

@Composable
fun ActionButtonsRow(
    modifier: Modifier = Modifier, onCancelClick: () -> Unit, onSaveClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(text = "Cancel", style = LocalTypography.current.label, fontSize = 16.sp)
        }

        Button(
            onClick = onSaveClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Save", style = LocalTypography.current.label, fontSize = 16.sp)
        }
    }
}