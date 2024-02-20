package com.lucao.hqawasomeapp

enum class Operations {
    SUM,
    MULTIPLICATION,
    DIVISION,
    MINUS
}

class Calculator {

    fun operation(operations: Operations, number1: Int, number2: Int) =
        when (operations) {
            Operations.SUM -> sum(number1, number2)
            Operations.MULTIPLICATION -> multiplication(number1, number2)
            Operations.DIVISION -> division(number1, number2)
            Operations.MINUS -> minus(number1, number2)

        }

    private fun sum(number1: Int, number2: Int) = number1 + number1
    private fun multiplication(number1: Int, number2: Int) = number1 * number2
    private fun division(number1: Int, number2: Int) = number1 / number2
    private fun minus(number1: Int, number2: Int) = number1 - number2
}
