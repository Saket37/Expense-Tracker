package com.example.expensetracker.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensetracker.designsystem.theme.LocalTypography

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
    showCharLimit: Boolean = false,
    imeAction: ImeAction,
    maxLength: Int = Int.MAX_VALUE,
    onTextChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = text,
            onValueChange = { newText ->
                if (newText.length <= maxLength) {
                    onTextChange(newText)
                }
            },
            placeholder = { Text(placeholderText) },
            label = { Text(labelText) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
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
        if (showCharLimit && isFocused)
            Text(
                text = "${text.length} / $maxLength",
                style = LocalTypography.current.body,
                color = Color.Red.copy(0.7f),
                modifier = Modifier
                    .padding(end = 28.dp, top = 12.dp)
                    .align(Alignment.TopEnd)

            )
    }

}