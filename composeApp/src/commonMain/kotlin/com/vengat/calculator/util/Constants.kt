package com.vengat.calculator.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val CalcBgGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFEFE6F5),/*Color(0xFFFFF9F8),*/Color(0xFFDBDAFB)),
    start = Offset.Zero,
    end = Offset(0f, Float.POSITIVE_INFINITY)
)