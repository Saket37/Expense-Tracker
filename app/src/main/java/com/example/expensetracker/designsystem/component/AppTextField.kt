package com.example.expensetracker.designsystem.component

import android.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    textColor: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.surface,
    focusedBorderColor: Color = MaterialTheme.colorScheme.surface,
    text: String,
    labelText: String,
    placeholderText: String,
    singleLine: Boolean,
    imeAction: ImeAction,
    onTextChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = { newText ->
            onTextChange(newText)
        },
        placeholder = { Text(placeholderText) },
        label = { Text(labelText) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = imeAction),
        singleLine = singleLine,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = containerColor,
            focusedContainerColor = containerColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
        ),
        shape = RoundedCornerShape(16.dp)
    )
}