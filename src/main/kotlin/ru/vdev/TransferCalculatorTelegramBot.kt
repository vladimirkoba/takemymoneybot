package ru.vdev

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException


/**
 * User: Vladimir Koba
 * Date: 25.02.2018
 * Time: 20:17
 */


class TransferCalculatorTelegramBot : TelegramLongPollingBot() {
    val chats: MutableList<Chat> = mutableListOf()

    override fun getBotUsername(): String {
        return "TransferCalculatorBot"
        //возвращаем юзера
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.hasChatId()) {
            val text = update.message.text

            val chat = chats.find { it.id == update.message.chatId } ?: Chat(update.message.chatId, mutableListOf())
            chats.addIfNotExist(chat)

            when (text) {
                "/+" -> chat.addManIfNotExist(Man(update.message.from?.firstName + " " + update.message.from?.lastName))
            }
        }

        println("Kukareku")
    }


    override fun getBotToken(): String {
        return "533636785:AAHE2goLSnU4Mb4E9w_-m4rqkGdlmcgqv8M"
        //Токен бота
    }


}


fun main(args: Array<String>) {
    ApiContextInitializer.init() // Инициализируем апи
    val botapi = TelegramBotsApi()
    try {
        botapi.registerBot(TransferCalculatorTelegramBot())
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }

}

fun MutableList<Chat>.addIfNotExist(chat: Chat) {
    if (this.find { it.id == chat.id } == null) {
        this.add(chat)
    }
}

fun Update.hasChatId(): Boolean = this.message?.chatId != null

fun String.deleteAllWhitespaces(): String {
    return this.replace("\\s".toRegex(), "")
}