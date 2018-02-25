package ru.vdev

import java.util.*

/**
 * User: Vladimir Koba
 * Date: 24.02.2018
 * Time: 22:45
 */


class Chat(val id: Long,
           private val people: MutableList<Man>) {
    private val transactions: Transactions = Transactions()

    fun addMan(man: Man) {
        people.add(man)
    }


    fun addManIfNotExist(man: Man) {
        if (!hasMan(man)) {
            people.add(man)
        }
    }

    private fun hasMan(man: Man): Boolean {
        return people.find { it.name == man.name } != null
    }

    fun deleteMan(man: Man) {
        people.remove(man)
    }

    fun addSum(name: String, sum: Money) {
        transactions.addTransaction(Transaction(name, sum))
    }

    fun showLog(): String {
        return transactions.showLog()
    }

    fun deleteTransaction(id: String): Boolean {
        return transactions.removeById(id)
    }

    fun calculate(): List<Transfer> {
        return TransferCalculator(transactions.peopleForTransferCalculation(people)).calculete()
    }

}

class Transactions(val transactions: MutableList<Transaction> = mutableListOf()) {
    fun addTransaction(t: Transaction) {
        transactions.add(t)
    }

    fun showLog(): String {
        val sb = StringBuilder()
        sb.append("Wasted:")
        for (t in transactions) {
            sb.append(t)
        }
        return sb.toString()
    }

    fun removeById(id: String): Boolean {
        return transactions.removeIf { it.id == id }
    }

    fun peopleForTransferCalculation(peopleFromChat: MutableList<Man>): List<Man> {
        for (man in peopleFromChat) {
            transactions
                    .filter { man.name == it.name }
                    .forEach { man.addWasted(it.sum) }
        }
        return peopleFromChat.toList()
    }
}

data class Transaction(val name: String,
                       val sum: Money,
                       val id: String = UUID.randomUUID().toString())


data class Man(
        val name: String,
        var wasted: Money = Money(0)
) {
    fun addWasted(money: Money) {
        wasted += money
    }
}