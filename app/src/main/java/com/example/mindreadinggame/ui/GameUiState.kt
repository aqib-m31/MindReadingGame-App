package com.example.mindreadinggame.ui

data class GameUiState(
    val currentCard: Int = -1,
    val isGameFinished: Boolean = false,
    val guessedNumber: Int = 0,
)