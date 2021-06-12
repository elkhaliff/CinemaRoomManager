package cinema

import kotlin.math.pow
import kotlin.math.round

// import java.math.RoundingMode

/**
 * �������� ����� ����������
 *  @author Andrey Zotov aka OldFox
 *  rows - ���������� �����
 *  seats - ���������� ���� � ����
 */
class RoomManager(val rows: Int, val seats: Int) {
    private var cntCells = 0 // ���������� ������� ����
    private var percentage: Double = 0.00 // ������� ������� ����
    private var currIncome = 0 // ������� �����
    private val ticket = "B" // ��������� ����� �� �����
    private val empty = "S" // ��������� �����

    /**
     * ������������� ������� ������� ������� ��������
     */
    private val fieldMap: Array<Array<String>> = Array(rows, { Array(this.seats, {"$empty"}) })

    private val priceMap: Array<Array<Int>> = Array(rows, { Array(this.seats, {10}) })

    /**
     * ��������� ��� �� ����� (�������� ���� �� ����� � ������� ����� >60)
     */
    fun setPriceMap() {
        if ((rows * seats) > 60 ) {
            val lowPrice: Int = rows - rows / 2
            for (r in rows-lowPrice until rows) {
                for (s in 0 until seats) {
                    priceMap[r][s] = 8
                }
            }
        }
    }

    /**
     * ������� ������ �� ����
     */
    fun buyTicket(row: Int, seat: Int) {
        fieldMap[row-1][seat-1] = ticket
    }

    /**
     * ������ ���� ������ �� ����
     */
    fun getTicketPrice(row: Int, seat: Int) =
        priceMap[row-1][seat-1]
    /**
     * ���������� �� p-������ ����� �������
     */
    fun toRound(d: Double, p: Int): String {
        var str = ""
        if (d != 0.0) {
            str = round(d * (10.0).pow(p)).toInt().toString()
        } else str = "000"

        val l = str.length
        str = str.substring(0, l-p)+"."+str.substring(l-p, l)
        return str
    }

    /**
     * ������ ���������� �� ��������� �������
     */
    fun stat(){
        cntCells = 0
        currIncome = 0
        fieldMap.forEachIndexed { row, r ->
            r.forEachIndexed { seat, s ->
                when (s) {
                    ticket -> {
                        cntCells++
                        currIncome += priceMap[row][seat]
                    }
                }
            }
        }
        percentage = (cntCells.toDouble() / (rows * seats).toDouble()) * 100
//        percentage = percentage.toBigDecimal().setScale(2, RoundingMode.CEILING).toDouble()
    }

    /**
     * �������� �� ��������� �����?
     */
    fun isEmpty(row: Int, seat: Int) = (fieldMap[row-1][seat-1] == empty)

    /**
     * ������������ ������ �� ������� ���� ����������
     * (� ��������� - �������� ����������� ������ �� ������)
     */
    override fun toString(): String {
        var i = 1
        var mapString = ""
        val border = Array(this.seats, {" ${i++}"}).joinToString("")+"\n"

        mapString = "Cinema:"+"\n"
        mapString += " $border"

        i = 1
        fieldMap.forEach { row ->
            mapString += "${i++} "
            row.forEach { s ->
                mapString += "$s "
            }
            mapString += "\n"
        }

        return mapString
    }

    /**
     * ���� ���� ���������� ����
     */
    fun getPriceCinema(): Int {
        var price = 0
        priceMap.forEach { row ->
            row.forEach { s ->
                price += s
            }
        }
        return price
    }
    /**
     * ���� �� ��������� �����?
     */
    fun isEmptyCell() = (rows * seats - (cntCells))>0

    /**
     * ����� �����
     */
    fun takeTicket() {
        println("Enter a row number:")
        val row = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val seat = readLine()!!.toInt()
        if ((row > rows) || (seat > seats)) {
            println("Wrong input!")
            takeTicket()
        } else {
            if (isEmpty(row, seat)) {
                buyTicket(row, seat)
                println("Ticket price: \$${getTicketPrice(row, seat)}")
            } else {
                println("That ticket has already been purchased!")
                takeTicket()
            }
        }
    }

    fun showMenu() {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
    }

    fun sowStatistics() {
        stat()
        println("Number of purchased tickets: $cntCells")
        println("Percentage: ${toRound(percentage, 2)}%")
        println("Current income: \$$currIncome")
        println("Total income: \$${getPriceCinema()}")
    }
}

fun main() {
    // write your code here
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seats = readLine()!!.toInt()

    val cinema = RoomManager(rows, seats) // ������������� ���������� ������ � �������� ������� ����
    cinema.setPriceMap()
    var oper = -1

    while(oper != 0) {
        cinema.showMenu()
        oper = readLine()!!.toInt()
        when (oper) {
            1 -> println(cinema)
            2 -> cinema.takeTicket()
            3 -> cinema.sowStatistics()
        }
    }

//    println("Total income:")
//    println("\$${cinema.getPriceCinema()}")

//    println(cinema)
}