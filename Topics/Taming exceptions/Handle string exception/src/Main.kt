fun main() {
    val index = readLine()!!.toInt()
    val word = readLine()!!
    val warning = "There isn't such an element in the given string, please fix the index!"
    println(if (index < word.length && index >= 0) word[index] else warning)
}
