package com.vengat.calculator.core

import kotlin.math.pow

class StandardMathExpressionEvaluator : ExpressionEvaluator {

    override fun evaluate(expression: String): Double {
        val tokens = tokenize(expression)
        return evaluateTokens(tokens)
    }

    private fun tokenize(expr: String): List<String> {
        val tokens = mutableListOf<String>()
        var i = 0
        val cleaned = expr.replace(" ", "")

        while (i < cleaned.length) {
            when {
                cleaned[i].isDigit() || cleaned[i] == '.' -> {
                    val num = StringBuilder()
                    while (i < cleaned.length && (cleaned[i].isDigit() || cleaned[i] == '.')) {
                        num.append(cleaned[i])
                        i++
                    }
                    tokens.add(num.toString())
                }

                cleaned[i] in "+-*/()^%" -> {
                    tokens.add(cleaned[i].toString())
                    i++
                }

                else -> i++
            }
        }
        return tokens
    }

    private fun evaluateTokens(tokens: List<String>): Double {
        val output = mutableListOf<Double>()
        val operators = mutableListOf<String>()

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> output.add(token.toDouble())
                token == "(" -> operators.add(token)
                token == ")" -> {
                    while (operators.isNotEmpty() && operators.last() != "(") {
                        applyOperator(output, operators.removeAt(operators.size - 1) /* Safe way to remove last element */)
                    }
                    if (operators.isNotEmpty()) operators.removeAt(operators.size - 1) /* Safe way to remove last element */ // Remove '('
                }

                token in "+-*/^%" -> {
                    while (operators.isNotEmpty() &&
                        precedence(operators.last()) >= precedence(token) &&
                        operators.last() != "("
                    ) {
                        applyOperator(output, operators.removeAt(operators.size - 1) /* Safe way to remove last element */)
                    }
                    operators.add(token)
                }
            }
        }

        while (operators.isNotEmpty()) {
            applyOperator(output, operators.removeAt(operators.size - 1) /* Safe way to remove last element */)
        }

        return output.firstOrNull() ?: 0.0
    }

    private fun precedence(op: String): Int = when (op) {
        "+", "-" -> 1
        "*", "/", "%" -> 2
        "^" -> 3
        else -> 0
    }

    private fun applyOperator(output: MutableList<Double>, op: String) {
        if (output.size < 2) return

        val b = output.removeAt(output.size - 1) /* Safe way to remove last element */ // output.removeLast()
        val a =  output.removeAt(output.size - 1) /* Safe way to remove last element */ //output.removeLast()

        val result = when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            "%" -> a / 100 * b
            "^" -> a.toDouble().pow(b.toDouble()) //Math.pow(a, b)
            else -> 0.0
        }

        output.add(result)
    }
}
