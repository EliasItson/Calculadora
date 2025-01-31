package elias.jorge.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import elias.jorge.calculadora.R.*

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentInput = ""
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operation: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        display = findViewById(id.textViewScreen)

        val buttons = listOf(
            id.buttonNumber0 to "0", id.buttonNumber1 to "1", id.buttonNumber2 to "2",
            id.buttonNumber3 to "3", id.buttonNumber4 to "4", id.buttonNumber5 to "5",
            id.buttonNumber6 to "6", id.buttonNumber7 to "7", id.buttonNumber8 to "8",
            id.buttonNumber9 to "9"
        )

        buttons.forEach { (id, num) ->
            findViewById<Button>(id).setOnClickListener { updateDisplay(num) }
        }

        findViewById<Button>(id.buttonAdd).setOnClickListener { setOperation('+') }
        findViewById<Button>(id.buttonSubtract).setOnClickListener { setOperation('-') }
        findViewById<Button>(id.buttonMultiply).setOnClickListener { setOperation('*') }
        findViewById<Button>(id.buttonDivide).setOnClickListener { setOperation('/') }
        findViewById<Button>(id.buttonEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(id.buttonClear).setOnClickListener { clearAll() }
    }

    private fun updateDisplay(number: String) {
        currentInput += number
        display.text = currentInput
    }

    private fun setOperation(op: Char) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDouble()
            operation = op
            currentInput = ""
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operation != null) {
            secondNumber = currentInput.toDouble()
            val result = when (operation) {
                '+' -> firstNumber + secondNumber
                '-' -> firstNumber - secondNumber
                '*' -> firstNumber * secondNumber
                '/' -> if (secondNumber != 0.0) firstNumber / secondNumber else {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                }
                else -> return
            }
            display.text = result.toString()
            firstNumber = result
            currentInput = ""
            operation = null
        }
    }

    private fun clearAll() {
        firstNumber = 0.0
        secondNumber = 0.0
        currentInput = ""
        operation = null
        display.text = ""
    }
}