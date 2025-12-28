package com.vengat.calculator.presentation

data class CalcState(
    val display: String = "",
    val currentValue: Double = 0.0
)

enum class Numbers(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ZERO(0)
}

enum class Operations(val operation: String, val symbol: String) {
    ADD("+", "+"),
    SUBTRACT("-", "-"),
    MULTIPLY("*", "×"),
    DIVIDE("/", "÷"),
    EQUAL("=", "="),
    PLUS_MINUS_SIGN("+/-", "±"),
    DOT(".", "."),
    PERCENT("%", "%"),
    CLEAR("C", "C")
}