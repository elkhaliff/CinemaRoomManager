fun main() {
    // write your code here
    val arrChr = readLine()!!.toCharArray()
    var a = arrChr[0]
    for (i in 1 until arrChr.size) {
        if (a.toInt() + 1 != arrChr[i].toInt()) {
            println("false")
            return
        }
        a = arrChr[i]
    }
    println("true")
}
