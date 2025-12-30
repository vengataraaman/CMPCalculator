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
//        object Clear : CalcEvents()
//        object Delete : CalcEvents()
//        object Decimal : CalcEvents()
//        object Calculate : CalcEvents()
        data class OnUpdateDisplayString(val data: String) : CalcEvents()
        data class OnOperationClick(val operation: String) : CalcEvents()
        data class OnNumberClick(val number: Int) : CalcEvents()
    }

    fun onEvent(event: CalcEvents) {
        when (event) {
            is CalcEvents.OnOperationClick -> {

                when (event.operation) {
                    "C" -> clearDisplay()
                    "=" -> evaluateExpression()
                    else -> {
                        _calcState.update { it.copy(display = calcState.value.display + event.operation) }
                    }
                }
            }

            is CalcEvents.OnNumberClick -> {
                _calcState.update { it.copy(display = calcState.value.display + event.number) }
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
                val mathRegex = Regex("[\\d+\\-*/().\\s]*")
                if (event.data.matches(mathRegex)) {
                    _calcState.update { it.copy(display = event.data) }
                } else if(event.data.last() == '=') {
                    evaluateExpression()
                }
            }
        }
    }

    fun evaluateExpression() {
        val result = evaluator.evaluate(calcState.value.display)
        val formatWithoutTrailingZeros = result.toString().replace("\\.0+$".toRegex(), "")
        _calcState.update { it.copy(display = formatWithoutTrailingZeros, currentValue = result) }
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