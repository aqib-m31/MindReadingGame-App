package com.example.mindreadinggame.ui

import androidx.lifecycle.ViewModel
import com.example.mindreadinggame.data.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    fun startGame() {
        _uiState.update { currentState ->
            currentState.copy(
                currentCard = 0
            )
        }
    }

    fun onExists(card: Card) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCard = currentState.currentCard + 1,
                guessedNumber = currentState.guessedNumber + Math.pow(2.0, card.number.toDouble()).toInt(),
                isGameFinished = card.number == 4
            )
        }
    }

    fun onDoesNotExist(cardNum: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCard = currentState.currentCard + 1,
                isGameFinished = cardNum == 4
            )
        }
    }

    fun resetGame() {
        _uiState.update { currentState ->
            currentState.copy(
                currentCard = -1,
                isGameFinished = false,
                guessedNumber = 0
            )
        }
    }
}