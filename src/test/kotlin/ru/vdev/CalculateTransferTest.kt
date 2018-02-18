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
        println(expectedTransfers)
        assertThat(TransferCalculator(man).calculete().sortedBy { it.from }).containsAll(expectedTransfers.sortedBy { it.from })
    }

    @Test
    fun returnTwoTransferWhenFuzzyDebt() {
        val vasya = Man("Vasya", Money(10000, Currency.RUB))
        val petya = Man("Petya", Money(0, Currency.RUB))
        val dasha = Man("Dasha", Money(0, Currency.RUB))
        val man = listOf(
                vasya,
                petya,
                dasha
        )
        val expectedTransfers = listOf(
                Transfer(petya.name, vasya.name, Money(3333, Currency.RUB)),
                Transfer(dasha.name, vasya.name, Money(3333, Currency.RUB))
        )
        println(expectedTransfers)
        assertThat(TransferCalculator(man).calculete().sortedBy { it.from }).containsAll(expectedTransfers.sortedBy { it.from })
    }

    @Test
    fun complexTest() {
        val vasya = Man("Vasya", Money(2500, Currency.RUB))
        val petya = Man("Petya", Money(1000, Currency.RUB))
        val dasha = Man("Dasha", Money(5500, Currency.RUB))
        val ira = Man("Ira", Money(1000, Currency.RUB))
        val man = listOf(
                vasya,
                petya,
                dasha,
                ira
        )
        val expectedTransfers = listOf(
                Transfer(petya.name, dasha.name, Money(1500, Currency.RUB)),
                Transfer(ira.name, dasha.name, Money(1500, Currency.RUB))
        )
        println(expectedTransfers)
        assertThat(TransferCalculator(man).calculete().sortedBy { it.from }).containsAll(expectedTransfers.sortedBy { it.from })
    }

    @Test
    fun rukaTest() {
        val anton = Man("Anton", Money(253, Currency.RUB))
        val anya = Man(" Anya", Money(0, Currency.RUB))
        val boris = Man("Boris", Money(170, Currency.RUB))
        val pavel = Man("Pavel", Money(57, Currency.RUB))
        val yulia = Man("Yulia", Money(0, Currency.RUB))
        val vova = Man("Vova", Money(188, Currency.RUB))
        val man = listOf(
                anton,
                boris,
                pavel,
                yulia,
                vova,
                anya
        )
        val expectedTransfers = listOf(
                Transfer(boris.name, pavel.name, Money(1500, Currency.RUB)),
                Transfer(vova.name, pavel.name, Money(1500, Currency.RUB))
        )
        println(TransferCalculator(man).calculete().sortedBy { it.from })
//        assertThat(TransferCalculator(man).calculete().sortedBy { it.from }).containsAll(expectedTransfers.sortedBy { it.from })
    }


}
