package com.vengat.calculator.presentation

import androidx.lifecycle.ViewModel
import com.vengat.calculator.core.ExpressionEvaluator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel constructor(
    val evaluator: ExpressionEvaluator
) : ViewModel() {

    var _calcState = MutableStateFlow(CalcState())
    val calcState = _calcState.asStateFlow()

    sealed class CalcEvents {
        data class OnUpdateDisplayString(val data: String) : CalcEvents()
        data class OnOperationClick(val operation: String) : CalcEvents()
        data class OnNumberClick(val number: Int) : CalcEvents()
    }

    fun onEvent(event: CalcEvents) {
        when (event) {
            is CalcEvents.OnOperationClick -> {

                when (event.operation) {
                    Operations.CLEAR.operation -> clearDisplay()
                    Operations.EQUAL.operation -> onEqualPressed()
                    Operations.BACK_SPACE.operation -> onBackSpacePressed()
                    Operations.BRACES.operation -> onBracesPressed()
                    else -> {
                        _calcState.update { it.copy(display = calcState.value.display + event.operation) }
                    }
                }
            }

            is CalcEvents.OnNumberClick -> {
                //_calcState.update { it.copy(display = calcState.value.display + event.number) }
                handleExpressionInput(calcState.value.display + event.number)
            }

            is CalcEvents.OnUpdateDisplayString -> {
                /**
                 * \d	Any digit (0-9)
                 * +	The literal plus sign +
                 * \-	The literal minus sign - (escaped because - can define ranges in [])
                 * *	The literal asterisk * (multiplication)
                 * /	The literal forward slash / (division)
                 * (	Left parenthesis (
                 * )	Right parenthesis )
                 * .	Literal dot . (decimal point)
                 * \s	Any whitespace (space, tab, etc.)
                 */
                val mathRegex = Regex("[\\d+\\-*/().%\\s]*")
                if (event.data.matches(mathRegex)) {
                    handleExpressionInput(event.data)
                } else if (event.data.last() == '=') {
                    onEqualPressed()
                }
            }
        }
    }

    fun onBracesPressed() {
        /**
         * Rule used by most calculators
         * Insert ( if:
         *     No open bracket exists yet, OR
         *     Last character is an operator + - × / (
         * Insert ) if:
         *     There is an unmatched ( already
         */
        if (calcState.value.display.isEmpty()) {
            _calcState.update { it.copy(display = calcState.value.display + "(") }
            return
        }

        val openCount = calcState.value.display.count { it == '(' }
        val closeCount = calcState.value.display.count { it == ')' }

        val lastChar = calcState.value.display.last()
        val operators = setOf('+', '-', '×', '*', '/', '(')

        if (openCount == closeCount || operators.contains(lastChar)) {
            _calcState.update { it.copy(display = calcState.value.display + "(") }
        } else {
            _calcState.update { it.copy(display = calcState.value.display + ")") }
        }
    }

    fun handleExpressionInput(data: String) {
        /**
         * Say if
         *  9* then don't execute evaluation
         *  9*9 then execute evaluation
         *  9 then don't execute evaluation as there is no operation
         */
        if (data.isNotEmpty() && data.last().isDigit() && data.any { !it.isDigit() }) {
            val result = evaluator.evaluate(data)
            _calcState.update { it.copy(display = data, currentValue = result) }
        } else {
            _calcState.update { it.copy(display = data) }
        }
    }

    fun onBackSpacePressed() {
        _calcState.update { it.copy(display = calcState.value.display.dropLast(1)) }
    }

    fun onEqualPressed() {
        val result = evaluator.evaluate(calcState.value.display)
        val formatWithoutTrailingZeros = result.toString().replace("\\.0+$".toRegex(), "")
        _calcState.update { it.copy(display = formatWithoutTrailingZeros, currentValue = 0.0) }
    }

    fun clearDisplay() {
        _calcState.update { it.copy(display = "", currentValue = 0.0) }
    }

    init {
        println("CalculatorViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        println("CalculatorViewModel cleared")
    }
}