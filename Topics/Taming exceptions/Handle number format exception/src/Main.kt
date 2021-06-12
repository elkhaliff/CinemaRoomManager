fun parseCardNumber(cardNumber: String): Long {
    val numbers = cardNumber.split(' ')
    return (numbers[0] + numbers[1] + numbers[2] + numbers[3]).toLong()
}
