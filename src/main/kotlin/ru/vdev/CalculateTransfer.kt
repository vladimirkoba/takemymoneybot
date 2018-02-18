package ru.vdev


fun main(args: Array<String>) {
    println("Hello, World")
}


data class Man(
        val name: String,
        val wasted: Money
)

data class Money(
        val cents: Int,
        val currency: Currency
)

data class Transfer(
        val from: Man,
        val to: Man,
        val amount: Money
)

enum class Currency {
    RUB, USD, EUR
}


class TransferCalculator(man: List<Man>) {
    fun calculete(): List<Transfer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
