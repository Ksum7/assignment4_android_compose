package mmcs.assignment4_compose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel: ViewModel(), Calculator {
    var _display = MutableStateFlow<String>("0")
    override var display = _display.asStateFlow()
    override fun addDigit(dig: Int) {
        TODO("Not yet implemented")
    }

    override fun addPoint() {
        TODO("Not yet implemented")
    }

    override fun addOperation(op: Operation) {
        TODO("Not yet implemented")
    }

    override fun compute() {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}