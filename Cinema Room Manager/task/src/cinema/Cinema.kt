package cinema

import kotlin.math.pow
import kotlin.math.round

// import java.math.RoundingMode

/**
 * Основной класс приложения
 *  @author Andrey Zotov aka OldFox
 *  rows - Количество рядов
 *  seats - Количество мест в ряде
 */
class RoomManager(val rows: Int, val seats: Int) {
    private var cntCells = 0 // Количество занятых мест
    private var percentage: Double = 0.00 // Процент занятых мест
    private var currIncome = 0 // Текущий доход
    private val ticket = "B" // Проданный билет на место
    private val empty = "S" // Свободное место

    /**
     * Инициализация массива рабочей области кинозала
     */
    private val fieldMap: Array<Array<String>> = Array(rows, { Array(this.seats, {"$empty"}) })

    private val priceMap: Array<Array<Int>> = Array(rows, { Array(this.seats, {10}) })

    /**
     * Установка цен на места (снижение цены за место в больших залах >60)
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
     * Продажа одного из мест
     */
    fun buyTicket(row: Int, seat: Int) {
        fieldMap[row-1][seat-1] = ticket
    }

    /**
     * Запрос цены одного из мест
     */
    fun getTicketPrice(row: Int, seat: Int) =
        priceMap[row-1][seat-1]
    /**
     * Округление до p-знаков после запятой
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
     * Расчет статистики по купленным билетам
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
     * Свободно ли указанное место?
     */
    fun isEmpty(row: Int, seat: Int) = (fieldMap[row-1][seat-1] == empty)

    /**
     * Формирование строки из массива зала кинотеатра
     * (в частности - получаем возможность вывода на печать)
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
     * Цена всех посадочных мест
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
     * Есть ли свободные места?
     */
    fun isEmptyCell() = (rows * seats - (cntCells))>0

    /**
     * Взять билет
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

    val cinema = RoomManager(rows, seats) // Инициализация экземпляра класса с заданной шириной поля
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