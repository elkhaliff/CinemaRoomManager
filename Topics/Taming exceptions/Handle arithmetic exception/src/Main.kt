fun main() {
    // put your code here
    val (a, b) = Array(2) { readLine()!!.toInt() }
    print(if (b != 0) a / b else "Division by zero, please fix the second argument!")
}
