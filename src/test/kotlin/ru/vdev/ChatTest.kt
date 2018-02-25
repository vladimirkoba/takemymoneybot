package ru.vdev

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * User: Vladimir Koba
 * Date: 24.02.2018
 * Time: 22:54
 */
class ChatTest {

    
    @Test
    fun transactionLogCreateRightPeopleList() {
        val transactions = Transactions()
        transactions.addTransaction(Transaction("Vasya", Money(100)))
        transactions.addTransaction(Transaction("Vasya", Money(50)))

        val peopleForTransferCalculation = transactions.peopleForTransferCalculation(mutableListOf(Man("Vasya"), Man("Zetya")))

        assertThat(peopleForTransferCalculation).hasSize(2)
        assertThat(peopleForTransferCalculation[0].wasted).isEqualTo(Money(150))
        assertThat(peopleForTransferCalculation[1].wasted).isEqualTo(Money(0))
    }
}