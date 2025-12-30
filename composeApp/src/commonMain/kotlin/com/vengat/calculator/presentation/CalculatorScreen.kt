package com.vengat.calculator.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.vengat.calculator.core.StandardMathExpressionEvaluator
import com.vengat.calculator.presentation.CalculatorViewModel.CalcEvents
import com.vengat.calculator.presentation.designsystem.CalcButton
import com.vengat.calculator.presentation.designsystem.CalcTextField
import com.vengat.calculator.util.CalcBgGradient
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = koinViewModel()
) {
    val calcState = viewModel.calcState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .background(CalcBgGradient)
                .fillMaxSize(),
        ) {
//            BoxWithConstraints {
//                Text("[W: ${maxWidth}, H: ${maxHeight}]")
//            }
            CalculatorResult(
                calcState.value,
                viewModel::onEvent,
                modifier = Modifier.padding(innerPadding).weight(2f)
            )
            CalculatorNumPad(
                calcState.value,
                viewModel::onEvent,
                modifier = Modifier.padding(innerPadding).weight(3f)
            )
        }
    }
}

@Composable
fun CalculatorResult(
    calcState: CalcState = CalcState(),
    onEvent: (event: CalcEvents) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        CalcTextField(
            text = calcState.display,
            onValueChange = { onEvent(CalcEvents.OnUpdateDisplayString(it)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CalculatorNumPad(
    calcState: CalcState = CalcState(),
    onEvent: (event: CalcEvents) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        var widthOfEachElement by remember { mutableStateOf(56.dp) }
        var heightOfEachElement by remember { mutableStateOf(56.dp) }
        BoxWithConstraints {
            // this 4.dp is needed for the end passing as we add only start and bottom padding for buttons
            widthOfEachElement = (maxWidth - (1 * 4.dp)) / 4
            heightOfEachElement = (maxHeight - (1 * 4.dp)) / 5
        }

        val buttonModifier = Modifier
            .height(heightOfEachElement)
            .width(widthOfEachElement)
            //.height(56.dp).width(56.dp)
            .padding(start = 4.dp, bottom = 4.dp)

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OperationsButton(Operations.CLEAR, onEvent, buttonModifier)
            OperationsButton(Operations.PLUS_MINUS_SIGN, onEvent, buttonModifier)
            OperationsButton(Operations.PERCENT, onEvent, buttonModifier)
            OperationsButton(Operations.DIVIDE, onEvent, buttonModifier)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberButton(Numbers.SEVEN, onEvent, buttonModifier)
            NumberButton(Numbers.EIGHT, onEvent, buttonModifier)
            NumberButton(Numbers.NINE, onEvent, buttonModifier)
            OperationsButton(Operations.MULTIPLY, onEvent, buttonModifier)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberButton(Numbers.FOUR, onEvent, buttonModifier)
            NumberButton(Numbers.FIVE, onEvent, buttonModifier)
            NumberButton(Numbers.SIX, onEvent, buttonModifier)
            OperationsButton(Operations.SUBTRACT, onEvent, buttonModifier)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberButton(Numbers.ONE, onEvent, buttonModifier)
            NumberButton(Numbers.TWO, onEvent, buttonModifier)
            NumberButton(Numbers.THREE, onEvent, buttonModifier)
            OperationsButton(Operations.ADD, onEvent, buttonModifier)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberButton(Numbers.ZERO, onEvent, buttonModifier)
            OperationsButton(Operations.DOT, onEvent, buttonModifier)
            // modifier is immutable , once the property is set we cannot modify it
            OperationsButton(
                Operations.EQUAL, onEvent,
                Modifier.height(heightOfEachElement)
                    .width(2 * widthOfEachElement)
                    .padding(start = 4.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
fun NumberButton(
    numbers: Numbers,
    onEvent: (event: CalcEvents) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    CalcButton(numbers.value.toString(), { onEvent(CalcEvents.OnNumberClick(numbers.value)) }, modifier)
}

@Composable
fun OperationsButton(
    operation: Operations,
    onEvent: (event: CalcEvents) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    CalcButton(operation.symbol, { onEvent(CalcEvents.OnOperationClick(operation.operation)) }, modifier)
}

@Composable
@Preview(name = "Light Mode", showBackground = true)
fun LoginScreenPreview() {
    MaterialTheme {
        //CalculatorNumPad()
        CalculatorScreen(CalculatorViewModel(StandardMathExpressionEvaluator()))
    }
}