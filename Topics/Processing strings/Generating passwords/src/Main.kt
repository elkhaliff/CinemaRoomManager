fun main() {
    // write your code here
    val (a, b, c, n) = readLine()!!.split(' ').map { it.toInt() }

    val arA = Array<String>(26) { i -> ('a' + i).toString() }
    val arN = Array<String>(10) { i -> ('0' + i).toString() }
    arA.shuffle()
    val passwd = Array<String>(n, { "" })
    var m = 0
    for (i in 0 until n) {
        passwd[i] = arA[m]
        if (m > 24) m -= 25 else m++
    }

    for (i in 0 until a) {
        passwd[i] = passwd[i].toUpperCase()
    }
    m = 0
    for (i in a until a + c) {
        passwd[i] = arN[m]
        if (m > 8) m -= 9 else m++
    }
//    passwd.shuffle()

    println(passwd.joinToString(""))
}