fun main() {
    // write your code here
    val str = readLine()!!
    var rst = ""
    for (i in str.length - 1 downTo 0) rst += str[i]
    if (str == rst) {
        println("yes")
    } else {
        println("no")
    }
}