package com.example.mindreadinggame.data

object CardDataProvider {
    private val cards: List<Card> = listOf(
        Card(
            members = listOf(
                listOf(1, 3, 5, 7),
                listOf(9, 11, 13, 15),
                listOf(17, 19, 21, 23),
                listOf(25, 27, 29, 31)
            ),
            number = 0
        ),
        Card(
            members = listOf(
                listOf(2, 3, 6, 7),
                listOf(10, 11, 14, 15),
                listOf(18, 19, 22, 23),
                listOf(26, 27, 30, 31)
            ),
            number = 1
        ),
        Card(
            members = listOf(
                listOf(4, 5, 6, 7),
                listOf(12, 13, 14, 15),
                listOf(20, 21, 22, 23),
                listOf(28, 29, 30, 31)
            ),
            number = 2
        ),
        Card(
            members = listOf(
                listOf(8, 9, 10, 11),
                listOf(12, 13, 14, 15),
                listOf(24, 25, 26, 27),
                listOf(28, 29, 30, 31)
            ),
            number = 3
        ),
        Card(
            members = listOf(
                listOf(16, 17, 18, 19),
                listOf(20, 21, 22, 23),
                listOf(24, 25, 26, 27),
                listOf(28, 29, 30, 31)
            ),
            number = 4
        )
    )

    fun getCardData(): List<Card> {
        return cards
    }

    fun getCard(number: Int): Card {
        return cards[number]
    }

    val default = getCard(0)
}