package com.vengat.calculator.presentation.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CalcCurrentValueTextField(
    text: String,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    CalcTextField(
        text = text,
        onValueChange = onValueChange,
        modifier = modifier,
        readOnly = true,
        textStyle = MaterialTheme.typography.titleLarge.copy(
            textAlign = TextAlign.End,
        ),
        minLines = 1,
        maxLines = 1
    )
}

@Composable
fun CalcDisplayTextField(
    text: String,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    CalcTextField(
        text = text,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = MaterialTheme.typography.headlineLarge.copy(
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
        )
    )
}

@Composable
fun CalcTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(
        textAlign = TextAlign.End,
        fontWeight = FontWeight.Bold,
    ),
    minLines: Int = 3,
    maxLines: Int = 3,
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        //singleLine = true,
        readOnly = readOnly,
        minLines = minLines,
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = textStyle,
    )
}