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


}
