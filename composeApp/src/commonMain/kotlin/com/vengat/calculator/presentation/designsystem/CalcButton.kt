package com.vengat.calculator.presentation.designsystem

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vengat.calculator.presentation.Operations

@Composable
fun CalcButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    GradientButton(
        text,
        onClick = onClick,
        modifier = modifier
    )

//    CalcButton(
//        onClick = onClick,
//        modifier = modifier,
//        contentPadding = contentPadding,
//        content = {
//            Text(
//                text = text,
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//            )
//        }
//    )
}

@Composable
fun CalcButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3B5BE0), //MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        contentPadding = contentPadding,
        shape = RoundedCornerShape(20.dp),
        content = content,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 20.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        )
    )
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(24.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .background(getButtonColor(text, isPressed))
        //.background(gradiantBrush)
    ) {
        Text(
            text = text,
            //color = textColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

fun getButtonColor(text: String, isPressed: Boolean): Brush {
    val gradiantBrush = if (isPressed) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF3B5BE0),
                Color(0xFF4E73DF),
                Color(0xFF628BEE),
                Color(0xFF75A4F3),
                Color(0xFF89BCF8)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF89BCF8),
                Color(0xFF75A4F3),
                Color(0xFF628BEE),
                Color(0xFF4E73DF),
                Color(0xFF3B5BE0)
            )
        )
    }

    return try {
        if (text == Operations.EQUAL.symbol
            || text == Operations.ADD.symbol
            || text == Operations.SUBTRACT.symbol
            || text == Operations.MULTIPLY.symbol
            || text == Operations.DIVIDE.symbol
        ) {
            Brush.verticalGradient(
                colors = if (isPressed)
                    listOf(
                        Color(0xFFFF6F00),  // shadow
                        Color(0xFFFFA500), // main orange
                        Color(0xFFFFF1D6) // highlight
                    )
                else
                    listOf(
                        Color(0xFFFFF1D6), // highlight
                        Color(0xFFFFA500), // main orange
                        Color(0xFFFF6F00)  // shadow
                    )
            )
        } else if (text == Operations.BACK_SPACE.symbol) {
            SolidColor(Color.Transparent)
        } else if (text == Operations.CLEAR.symbol || text == Operations.BRACES.symbol || text == Operations.PERCENT.symbol) {
            Brush.verticalGradient(
                colors = if (isPressed)
                    listOf(Color(0xFF9C27B0), Color(0xFFD1A1FF), Color(0xFFC9B3FF))
                else
                    listOf(Color(0xFFC9B3FF), Color(0xFFD1A1FF), Color(0xFF9C27B0))
            )
        } else if (Operations.entries.find { it.symbol == text } != null) {
            // for all symbols
            //SolidColor(Color(0xFFF44336))
            gradiantBrush
        } else {
            gradiantBrush
        }
    } catch (e: IllegalArgumentException) {
        gradiantBrush
    }
}
