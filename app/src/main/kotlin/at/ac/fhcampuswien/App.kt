/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package at.ac.fhcampuswien

class App {
    // Game logic for a number guessing game
    fun playNumberGame(digitsToGuess: Int = 4) {
        val generatedNumber = generateRandomNonRepeatingNumber(digitsToGuess)
        println("Guess a number with $digitsToGuess unique digits:")

        var attempts = 0
        while (attempts < 4) {
            try {
                val userInput = readLine()!!.toInt()
                // Validate the length of the user input
                if (userInput.toString().length != digitsToGuess) {
                    println("Invalid input: Please enter a number with exactly $digitsToGuess digits.")
                    continue // Prompt the user for input again
                }

                val result = checkUserInputAgainstGeneratedNumber(userInput, generatedNumber)
                if (result.m == digitsToGuess) {
                    println("Congratulations! You've guessed the number correctly.")
                    break
                } else {
                    // Provide feedback based on the attempt
                    println("You have ${result.n} correct digits, with ${result.m} in the correct position. Try again:")
                }
                attempts++
            } catch (e: NumberFormatException) {
                // Handle case where input is not an integer
                println("Invalid input: Please enter a valid number.")
            }
        }

        if (attempts == 4) {
            println("Sorry, you've used all your attempts. The correct number was $generatedNumber.")
        }
    }

    /**
     * Generates a non-repeating number of a specified length between 1-9.
     *
     * Note: The function is designed to generate a number where each digit is unique and does not repeat.
     * It is important to ensure that the length parameter does not exceed the maximum possible length
     * for non-repeating digits (which is 9 excluding 0 for base-10 numbers).
     *
     * @param length The length of the non-repeating number to be generated.
     *               This dictates how many digits the generated number will have.
     * @return An integer of generated non-repeating number.
     *         The generated number will have a number of digits equal to the specified length and will
     *         contain unique, non-repeating digits.
     * @throws IllegalArgumentException if the length is more than 9 or less than 1.
     */
    val generateRandomNonRepeatingNumber: (Int) -> Int = { length ->
        //TODO implement the function
        if (length < 1 || length > 9) {
            throw IllegalArgumentException("Length must be between 1 and 9")
        }
        val digits = ('1'..'9').shuffled().take(length).joinToString("").toInt()
        digits
    }

    /**
     * Compares the user's input integer against a generated number for a guessing game.
     * This function evaluates how many digits the user guessed correctly and how many of those
     * are in the correct position. The game generates number with non-repeating digits.
     *
     * Note: The input and the generated number must both be numbers.
     * If the inputs do not meet these criteria, an IllegalArgumentException is thrown.
     *
     * @param input The user's input integer. It should be a number with non-repeating digits.
     * @param generatedNumber The generated number with non-repeating digits to compare against.
     * @return [CompareResult] with two properties:
     *         1. `n`: The number of digits guessed correctly (regardless of their position).
     *         2. `m`: The number of digits guessed correctly and in the correct position.
     *         The result is formatted as "Output: m:n", where "m" and "n" represent the above values, respectively.
     * @throws IllegalArgumentException if the inputs do not have the same number of digits.
     */
    val checkUserInputAgainstGeneratedNumber: (Int, Int) -> CompareResult = { input, generatedNumber ->
        val inputStr = input.toString()
        val generatedStr = generatedNumber.toString()
        if (inputStr.length != generatedStr.length) throw IllegalArgumentException("Input and generated number must have the same number of digits.")

        var m = 0
        var n = 0
        val generatedDigitsCount = IntArray(10) // Array to count occurrences of each digit in the generated number
        val inputDigitsCount = IntArray(10) // Array to count occurrences of each digit in the input

        // Fill in the occurrences of each digit
        for (i in inputStr.indices) {
            val inputDigit = inputStr[i] - '0'
            val generatedDigit = generatedStr[i] - '0'
            if (inputDigit == generatedDigit) {
                m++
            }
            inputDigitsCount[inputDigit]++
            generatedDigitsCount[generatedDigit]++
        }

        // Calculate correctDigits based on the minimum occurrences between input and generated numbers
        for (i in 1..9) { // Digits range is 1-9
            n += minOf(inputDigitsCount[i], generatedDigitsCount[i])
        }

        CompareResult(n, m)
    }


}

fun main() {
    println("Hello World!")
    // TODO: call the App.playNumberGame function with and without default arguments
    val app = App()
    app.playNumberGame(4)
    app.playNumberGame()
}
