fun main() {
    // write your code here
    val arrStr = readLine()!!.split(' ')
    var l = 0
    var max = ""
    arrStr.forEach {
        if (it.length > l) {
            l = it.length
            max = it
        }
    }
    println(max)
}
