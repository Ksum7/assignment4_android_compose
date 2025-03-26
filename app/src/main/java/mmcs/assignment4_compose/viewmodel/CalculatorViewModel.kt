package mmcs.assignment4_compose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel(), Calculator {
    private val _display = MutableStateFlow<String>("0")
    override var display: StateFlow<String> = _display.asStateFlow()

    private var currentNumber = StringBuilder()
    private var firstOperand = 0.0
    private var currentOperation: Operation? = null
    private var hasDecimal = false
    private var isNewNumber = true

    override fun addDigit(dig: Int) {
        if (isNewNumber) {
            currentNumber.clear()
            isNewNumber = false
        }

        if (currentNumber.toString() == "0" && dig == 0) return

        if (currentNumber.length < 15) {
            if (currentNumber.toString() == "0") {
                currentNumber.clear()
            }
            currentNumber.append(dig)
            updateDisplay()
        }
    }

    override fun addPoint() {
        if (!hasDecimal) {
            if (isNewNumber) {
                currentNumber.clear()
                currentNumber.append("0")
                isNewNumber = false
            }
            currentNumber.append(".")
            hasDecimal = true
            updateDisplay()
        }
    }

    override fun addOperation(op: Operation) {
        if (op == Operation.NEG && display.value != "0") {
            val result = -(display.value.toDoubleOrNull() ?: 0.0)
            val resultString = formatNumber(result)
            currentNumber.clear()
            currentNumber.append(resultString)
            updateDisplay()
        } else if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toString().toDouble()
            currentNumber.clear()
            currentOperation = op
            hasDecimal = false
            isNewNumber = true
        }
    }

    override fun compute() {
        if (currentNumber.isEmpty() || currentOperation == null) return

        val secondOperand = currentNumber.toString().toDouble()
        val result = when (currentOperation) {
            Operation.ADD -> firstOperand + secondOperand
            Operation.SUB -> firstOperand - secondOperand
            Operation.MUL -> firstOperand * secondOperand
            Operation.DIV -> {
                if (secondOperand == 0.0) {
                    _display.value = "Error: Division by zero"
                    resetState()
                    return
                }
                firstOperand / secondOperand
            }
            Operation.PERC -> firstOperand * (secondOperand / 100.0)
            else -> return
        }

        val resultString = formatNumber(result)
        resetState()
        currentNumber.append(resultString)
        _display.value = resultString
    }

    override fun clear() {
        currentNumber.clear()
        hasDecimal = false
        isNewNumber = false
        _display.value = "0"
    }

    override fun reset() {
        clear()
        firstOperand = 0.0
        currentOperation = null
        isNewNumber = true
    }

    private fun updateDisplay() {
        _display.value = currentNumber.toString()
    }

    private fun resetState() {
        currentNumber.clear()
        hasDecimal = false
        isNewNumber = true
        currentOperation = null
    }

    private fun formatNumber(number: Double): String {
        return if (number % 1 == 0.0) {
            number.toLong().toString()
        } else {
            String.format("%.8f", number).trimEnd('0').trimEnd('.')
        }
    }
}