package com.programperfect.messagesexpressive.core.model

import com.programperfect.messagesexpressive.core.designsystem.ExpressiveAccent

data class Conversation(
    val id: String,
    val name: String,
    val initials: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0,
    val pinned: Boolean = false,
    val accent: ExpressiveAccent
)

data class Message(
    val id: String,
    val text: String,
    val outgoing: Boolean,
    val time: String,
    val read: Boolean = true,
    val sticker: String? = null
)

val demoConversations = listOf(
    Conversation("hwan", "Хван", "ХВ", "Маршрут Грушина пришлю отдельной картой.", "15:42", 2, true, ExpressiveAccent.Violet),
    Conversation("nikolsky", "Никольский", "АН", "Посмотри документ на планшете.", "14:18", 0, true, ExpressiveAccent.Teal),
    Conversation("karaev", "Караев", "СК", "Выйду через служебный вход.", "13:07", 1, false, ExpressiveAccent.Coral),
    Conversation("prop", "Реквизит", "РК", "Готовы паспорт, ориентировка и маршрут.", "вчера", 0, false, ExpressiveAccent.Night)
)

val demoMessages = mapOf(
    "hwan" to listOf(
        Message("h1", "Никольский, я скинула тебе карту. Красная точка - конечная.", false, "15:35"),
        Message("h2", "Получил. Это по Грушину?", true, "15:36"),
        Message("h3", "Да. Документ тоже приложила. Не официальный рапорт, а служебная выписка.", false, "15:37"),
        Message("h4", "Понял. В кадре читаю с планшета.", true, "15:38"),
        Message("h5", "", false, "15:39", sticker = "NICE"),
        Message("h6", "Только не называй это делом. Это проверка по линии ведомств.", false, "15:42")
    ),
    "nikolsky" to listOf(
        Message("n1", "Хван прислала таблицу по счетам.", false, "14:12"),
        Message("n2", "Покажи Караеву. Пусть смотрит на связку фамилий.", true, "14:14"),
        Message("n3", "Он уже рядом. Открою на планшете.", false, "14:18")
    ),
    "karaev" to listOf(
        Message("k1", "Ты где?", true, "13:04"),
        Message("k2", "На месте. Машину поставил дальше от выезда.", false, "13:05"),
        Message("k3", "Не геройствуй.", true, "13:06"),
        Message("k4", "Поздно сказал.", false, "13:07", read = false)
    ),
    "prop" to listOf(
        Message("p1", "На сцену нужны маршрут, фото документа и экран СМС.", false, "вчера"),
        Message("p2", "Сделаю в Material-оболочке, чтобы выглядело как настоящее приложение.", true, "вчера")
    )
)
