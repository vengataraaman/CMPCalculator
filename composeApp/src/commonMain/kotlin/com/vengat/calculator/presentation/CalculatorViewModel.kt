package com.vengat.calculator.presentation

import androidx.lifecycle.ViewModel

class CalculatorViewModel constructor(

) : ViewModel() {
    init {
        println("CalculatorViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        println("CalculatorViewModel cleared")
    }
}