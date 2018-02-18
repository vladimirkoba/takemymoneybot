package ru.vdev

import kotlin.math.abs


fun main(args: Array<String>) {
    println("Hello, World")
}


data class Man(
        val name: String,
        val wasted: Money
)

data class Money(
        val cents: Int,
        val currency: Currency = Currency.RUB
) {
    operator fun plus(money: Money): Money {
        if (this.currency == money.currency) {
            return Money(this.cents + money.cents, this.currency)
        }
        throw UnsupportedOperationException("Different currency not implemented yet")
    }

    operator fun minus(money: Money): Money {
        if (this.currency == money.currency) {
            return Money(this.cents - money.cents, this.currency)
        }
        throw UnsupportedOperationException("Different currency not implemented yet")
    }
}

data class Transfer (
        val from: String,
        val to: String,
        val amount: Money
)

enum class Currency {
    RUB, USD, EUR
}


class TransferCalculator(val man: List<Man>) {
    fun calculete(): List<Transfer> {
        val (debtors, creditors) = man
                .map { Man(it.name, Money(it.wasted.cents - averageWasted(), it.wasted.currency)) }
                .filter { it.wasted.cents != 0 }
                .partition { it.wasted.cents < 0 }
        val transfers = mutableListOf<Transfer>()
        for (debtor in debtors) {
            val creditorForTransfer = creditors.minBy { (it.wasted + debtor.wasted).cents >= 0 }
                    ?: throw IllegalArgumentException("Incorrect input!")
            transfers.add(Transfer(debtor.name, creditorForTransfer.name, Money(abs(debtor.wasted.cents))))
        }
        return transfers
    }

    private fun findCreditorWithSameCredit(debtor: Man, creditors: List<Man>): Man? {
        return creditors.find { (it.wasted + debtor.wasted).cents == 0 }
    }

    private fun averageWasted() = man.sumBy { it.wasted.cents } / man.size

}
