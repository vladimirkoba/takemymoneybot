package ru.vdev

import kotlin.math.abs


fun main(args: Array<String>) {
    println("Hello, World")
}




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

data class Transfer(
        val from: String,
        val to: String,
        val amount: Money

) {
    override fun toString(): String {
        return "$from -> $to : ${amount.cents}${amount.currency} \n"
    }
}

enum class Currency {
    RUB, USD, EUR
}


class TransferCalculator(val man: List<Man>) {
    fun calculete(): List<Transfer> {
        val eps = man.size-1
        var (debtors, creditors) = man
                .map { Man(it.name, Money(it.wasted.cents - averageWasted(), it.wasted.currency)) }
                .filter { it.wasted.cents != 0 }
                .partition { it.wasted.cents < 0 }
        val transfers = mutableListOf<Transfer>()
        for (debtor in debtors) {
            var debt = debtor
            while (abs(debt.wasted.cents) >= eps) {
                var cred = findCreditorForTransfer(creditors, debtor, eps)

                val sumOfTransferCents = calculateSumOfTransfer(cred, debt)
                transfers.add(Transfer(debt.name, cred.name, Money(sumOfTransferCents)))

                debt = Man(debtor.name, Money(debt.wasted.cents + sumOfTransferCents))
                cred = Man(cred.name, Money(cred.wasted.cents - sumOfTransferCents))
                creditors = creditors.map { if (it.name == cred.name) cred else it } .filter { it.wasted.cents > eps }
            }
        }
        return transfers
    }

    private fun calculateSumOfTransfer(cred: Man, debt: Man): Int {
        return if (abs(cred.wasted.cents) >= abs(debt.wasted.cents)) {
            abs(debt.wasted.cents)
        } else {
            abs(cred.wasted.cents)
        }
    }

    private fun findCreditorForTransfer(creditors: List<Man>, debtor: Man, eps: Int): Man {
        var creditorForTransfer = findDirectCreditor(creditors, debtor, eps)
        if (creditorForTransfer == null) {
            creditorForTransfer = findCreditorWithMoreCreditThanDebt(creditors, debtor)
        }
        if (creditorForTransfer == null) {
            creditorForTransfer = creditors.find { it.wasted.cents < debtor.wasted.cents } ?: throw IllegalArgumentException("WTF")
        }
        return creditorForTransfer
    }

    private fun findCreditorWithMoreCreditThanDebt(creditors: List<Man>, debtor: Man) =
            creditors.find { it.wasted.cents > debtor.wasted.cents }

    private fun findDirectCreditor(creditors: List<Man>, debtor: Man, eps: Int): Man? {
        return creditors.minBy {
            (it.wasted + debtor.wasted).cents >= eps
        }
    }

    private fun findCreditorWithSameCredit(debtor: Man, creditors: List<Man>): Man? {
        return creditors.find { (it.wasted + debtor.wasted).cents == 0 }
    }

    private fun averageWasted() = man.sumBy { it.wasted.cents } / man.size

}
