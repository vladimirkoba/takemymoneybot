package ru.vdev

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class CalculateTransferTest {

    @Test
    fun returnEmptyTransfersWhenNoDebts() {
        val man = listOf<Man>(
                Man("Vasya", Money(10000, Currency.RUB)),
                Man("Petya", Money(10000, Currency.RUB)),
                Man("Kolya", Money(10000, Currency.RUB))
        )
        assertThat(TransferCalculator(man).calculete()).isEmpty()
    }

    @Test
    fun returnOneTransferWhenOneManCreditOtherMan() {
        val vasya = Man("Vasya", Money(10000, Currency.RUB))
        val petya = Man("Petya", Money(0, Currency.RUB))
        val man = listOf<Man>(
                vasya,
                petya
        )
        val expectedTransfers = listOf(Transfer(petya.name, vasya.name, Money(5000, Currency.RUB)))
        assertThat(TransferCalculator(man).calculete()).isEqualTo(expectedTransfers)
    }

    @Test
    fun returnTwoTransferWhenThreesome() {
        val vasya = Man("Vasya", Money(9000, Currency.RUB))
        val petya = Man("Petya", Money(0, Currency.RUB))
        val dasha = Man("Dasha", Money(0, Currency.RUB))
        val man = listOf(
                vasya,
                petya,
                dasha
        )
        val expectedTransfers = listOf(
                Transfer(petya.name, vasya.name, Money(3000, Currency.RUB)),
                Transfer(dasha.name, vasya.name, Money(3000, Currency.RUB))
        )
        assertThat(TransferCalculator(man).calculete().sortedBy { it.from }).containsAll(expectedTransfers.sortedBy { it.from })
    }


}
