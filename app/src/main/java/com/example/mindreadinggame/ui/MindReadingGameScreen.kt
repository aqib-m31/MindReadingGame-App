package com.example.mindreadinggame.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mindreadinggame.R
import com.example.mindreadinggame.data.Card
import com.example.mindreadinggame.data.CardDataProvider
import com.example.mindreadinggame.ui.theme.MindReadingGameTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindReadingGameApp() {
    val viewModel: GameViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            GameAppBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.currentCard == -1) {
                GameStartScreen(startGame = { viewModel.startGame() })
            } else if (!uiState.isGameFinished) {
                val card = CardDataProvider.getCard(uiState.currentCard)
                NumberCardScreen(
                    card = CardDataProvider.getCard(uiState.currentCard),
                    onExists = {
                        viewModel.onExists(card)
                    },
                    onDoesNotExist = {
                        viewModel.onDoesNotExist(card.number)
                    },
                    onRestart = {
                        viewModel.resetGame()
                    }
                )
            } else {
                GameEndButtonAndMessage(
                    guessedNumber = uiState.guessedNumber,
                    onPlayAgain = { viewModel.resetGame() }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.brain), contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.top_bar_icon_size))
                        .padding(end = dimensionResource(id = R.dimen.padding_medium))
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        },
    )
}

@Composable
fun GameStartScreen(
    startGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.instruction_message),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = startGame,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.start_game),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
                        .width(dimensionResource(id = R.dimen.start_btn_size))
                )
            }
        }
    }

}

@Composable
fun GameEndButtonAndMessage(
    guessedNumber: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.brain), contentDescription = null,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
        )
        Text(
            text = stringResource(R.string.result_message, guessedNumber),
            style = MaterialTheme.typography.displayMedium
        )
        PlayAgainButton(
            onClick = onPlayAgain,
            label = stringResource(R.string.play_again),
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large))
        )
    }
}

@Composable
fun PlayAgainButton(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun NumberCardScreen(
    card: Card,
    onExists: () -> Unit,
    onDoesNotExist: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Card(
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation)),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
                for (row in card.members) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        row.forEach { col ->
                            Text(
                                text = col.toString(),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineLarge,
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = stringResource(R.string.question_text),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_large))
        )

        NumberExistsController(
            onExists = onExists, onDoesNotExist = onDoesNotExist,
            onRestart = onRestart,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large))
        )
    }
}

@Composable
fun NumberExistsController(
    onExists: () -> Unit,
    onDoesNotExist: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onExists, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.yes), style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            Button(onClick = onDoesNotExist, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.no), style = MaterialTheme.typography.headlineMedium)
            }
        }
        PlayAgainButton(onClick = onRestart, label = stringResource(R.string.restart), modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
    }
}


@Preview(showBackground = true)
@Composable
fun NumberCardPreview() {
    MindReadingGameTheme {
        NumberCardScreen(
            card = CardDataProvider.getCard(0),
            {},
            {},
            {},
        )
    }
}

@Preview("MindReadingGame Preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MindReadingGameAppPreview() {
    MindReadingGameTheme {
        MindReadingGameApp()
    }
}