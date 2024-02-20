package com.lucao.hqawasomeapp


import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CalculatorTest {

    private lateinit var calculator: Calculator

    @Before
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun sum_whenReceivingNumber2And3_thenResult5() {
        //Given
        val number1 = 2
        val number2 = 3
        val operations = Operations.SUM

        //WHen
        val result = calculator.operation(operations, number1, number2)

        //Then
        assertThat(result).isEqualTo(5)
    }

    @Test
    fun multiplication_whenReceivingNumber2And3_thenResult6() {
        //Given
        val number1 = 2
        val number2 = 3
        val operations = Operations.MULTIPLICATION

        //WHen
        val result = calculator.operation(operations, number1, number2)

        //Then
        assertThat(result).isEqualTo(6)
    }
}