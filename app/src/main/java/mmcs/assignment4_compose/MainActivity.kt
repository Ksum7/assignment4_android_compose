package mmcs.assignment4_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mmcs.assignment4_compose.ui.theme.Assignment4_composeTheme
import mmcs.assignment4_compose.viewmodel.Operation
import mmcs.assignment4_compose.viewmodel.Calculator
import mmcs.assignment4_compose.viewmodel.CalculatorViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import mmcs.assignment4_compose.ui.theme.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    private val viewModel: CalculatorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment4_composeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun CalculatorScreen(viewModel: Calculator) {
    val display = viewModel.display.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(LightGray)
                .padding(8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = display,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val buttonModifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .padding(4.dp)

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("AC", buttonModifier, onClick = { viewModel.reset() })
                CalculatorButton("C", buttonModifier, onClick = { viewModel.clear() })
                CalculatorButton("%", buttonModifier, onClick = { viewModel.addOperation(Operation.PERC) })
                CalculatorButton("÷", buttonModifier, backgroundColor = Orange, textColor = OperatorTextColor, onClick = { viewModel.addOperation(Operation.DIV) })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("7", buttonModifier, onClick = { viewModel.addDigit(7) })
                CalculatorButton("8", buttonModifier, onClick = { viewModel.addDigit(8) })
                CalculatorButton("9", buttonModifier, onClick = { viewModel.addDigit(9) })
                CalculatorButton("×", buttonModifier, backgroundColor = Orange, textColor = OperatorTextColor, onClick = { viewModel.addOperation(Operation.MUL) })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("4", buttonModifier, onClick = { viewModel.addDigit(4) })
                CalculatorButton("5", buttonModifier, onClick = { viewModel.addDigit(5) })
                CalculatorButton("6", buttonModifier, onClick = { viewModel.addDigit(6) })
                CalculatorButton("−", buttonModifier, backgroundColor = Orange, textColor = OperatorTextColor, onClick = { viewModel.addOperation(Operation.SUB) })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("1", buttonModifier, onClick = { viewModel.addDigit(1) })
                CalculatorButton("2", buttonModifier, onClick = { viewModel.addDigit(2) })
                CalculatorButton("3", buttonModifier, onClick = { viewModel.addDigit(3) })
                CalculatorButton("+", buttonModifier, backgroundColor = Orange, textColor = OperatorTextColor, onClick = { viewModel.addOperation(Operation.ADD) })
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton("±", buttonModifier, onClick = { viewModel.addOperation(Operation.NEG) })
                CalculatorButton("0", buttonModifier, onClick = { viewModel.addDigit(0) })
                CalculatorButton(".", buttonModifier, onClick = { viewModel.addPoint() })
                CalculatorButton("=", buttonModifier, backgroundColor = Green, textColor = EqualsTextColor, onClick = { viewModel.compute() })
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier,
    backgroundColor: Color = White,
    textColor: Color = TextColor,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    Assignment4_composeTheme {
        CalculatorScreen(CalculatorViewModel())
    }
}