package com.example.jogodamemoria.ui.jogo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.Temas
import com.example.jogodamemoria.data.local.AppDatabase
import com.example.jogodamemoria.data.remote.FirebaseCartasService
import com.example.jogodamemoria.data.repository.CartasRepository
import com.example.jogodamemoria.data.repository.PartidasRepository

@Composable
fun TelaJogo(
    tema: String,
    dificuldade: String,
    nomePadrao: String,
    onVoltar: () -> Unit,
    viewModel: JogoViewModel = viewModel(
        factory = LocalContext.current.let { contexto ->
            remember(contexto, tema, dificuldade, nomePadrao) {
                val banco = AppDatabase.getDatabase(contexto)
                JogoViewModelFactory(
                    cartasRepository = CartasRepository(banco.cartaDAO(), FirebaseCartasService()),
                    partidasRepository = PartidasRepository(banco.partidaDAO()),
                    tema = tema,
                    dificuldade = dificuldade,
                    nomePadrao = nomePadrao
                )
            }
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConteudoJogo(
        tema = tema,
        dificuldade = dificuldade,
        uiState = uiState,
        onCartaClicada = viewModel::onCartaClicada,
        onNomeParaSalvarChange = viewModel::onNomeParaSalvarChange,
        onSalvarPartida = viewModel::onSalvarPartida,
        onReiniciar = viewModel::onReiniciar,
        onVoltar = onVoltar
    )
}

@Composable
private fun ConteudoJogo(
    tema: String,
    dificuldade: String,
    uiState: JogoUiState,
    onCartaClicada: (Int) -> Unit,
    onNomeParaSalvarChange: (String) -> Unit,
    onSalvarPartida: () -> Unit,
    onReiniciar: () -> Unit,
    onVoltar: () -> Unit
) {
    val comparandoPar = uiState.cartas.count { it.virada && !it.encontrada } == 2

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "$tema · ${Dificuldades.nomeExibicao(dificuldade)}",
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tempo: ${uiState.segundos} s")
            Text("Tentativas: ${uiState.tentativas}")
        }

        if (uiState.cartas.isEmpty()) {
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val erro = uiState.mensagemErro
                if (erro == null) {
                    Text("Carregando cartas…")
                } else {
                    Text(erro, color = MaterialTheme.colorScheme.error)
                    TextButton(onClick = onReiniciar) { Text("Tentar novamente") }
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.cartas, key = { it.id }) { carta ->
                    CartaDoTabuleiro(
                        carta = carta,
                        comparandoPar = comparandoPar,
                        onClick = { onCartaClicada(carta.id) }
                    )
                }
            }
        }
        TextButton(onClick = onVoltar) { Text("Voltar") }
    }

    if (uiState.venceu) {
        DialogoVitoria(
            uiState = uiState,
            onNomeParaSalvarChange = onNomeParaSalvarChange,
            onSalvarPartida = onSalvarPartida,
            onReiniciar = onReiniciar,
            onVoltar = onVoltar
        )
    }
}

@Composable
private fun CartaDoTabuleiro(carta: CartaJogo, comparandoPar: Boolean, onClick: () -> Unit) {
    val revelada = carta.virada || carta.encontrada
    val cores = MaterialTheme.colorScheme
    val estadoCarta = when {
        carta.encontrada -> "Par encontrado"
        carta.virada -> "Carta virada"
        else -> "Carta fechada"
    }
    val corCarta = when {
        carta.encontrada -> cores.secondaryContainer
        carta.virada -> cores.surfaceVariant
        else -> cores.primaryContainer
    }

    Card(
        onClick = onClick,
        enabled = !comparandoPar && !revelada,
        modifier = Modifier.aspectRatio(1f).semantics { stateDescription = estadoCarta },
        colors = CardDefaults.cardColors(
            containerColor = corCarta,
            contentColor = cores.onSurface,
            disabledContainerColor = corCarta,
            disabledContentColor = cores.onSurface
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = if (revelada) carta.simbolo else "?",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
private fun DialogoVitoria(
    uiState: JogoUiState,
    onNomeParaSalvarChange: (String) -> Unit,
    onSalvarPartida: () -> Unit,
    onReiniciar: () -> Unit,
    onVoltar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Você venceu!") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Pontuação: ${uiState.pontuacao}")
                TextField(
                    value = uiState.nomeParaSalvar,
                    onValueChange = onNomeParaSalvarChange,
                    label = { Text("Nome do jogador") },
                    singleLine = true,
                    enabled = !uiState.partidaSalva,
                    modifier = Modifier.fillMaxWidth()
                )
                if (uiState.partidaSalva) Text("Partida salva no ranking.")
                uiState.mensagemErro?.let { erro ->
                    Text(erro, color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Button(onClick = onSalvarPartida, enabled = !uiState.partidaSalva) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = onVoltar) { Text("Voltar") }
                TextButton(onClick = onReiniciar) { Text("Jogar de novo") }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TelaJogoPreview() {
    MaterialTheme {
        ConteudoJogo(
            tema = Temas.ANIMAIS,
            dificuldade = Dificuldades.FACIL,
            uiState = JogoUiState(
                cartas = listOf(
                    CartaJogo(id = 0, simbolo = "🐶", virada = true, encontrada = true),
                    CartaJogo(id = 1, simbolo = "🐱"),
                    CartaJogo(id = 2, simbolo = "🐶", virada = true, encontrada = true),
                    CartaJogo(id = 3, simbolo = "🐸"),
                    CartaJogo(id = 4, simbolo = "🦊"),
                    CartaJogo(id = 5, simbolo = "🐸", virada = true),
                    CartaJogo(id = 6, simbolo = "🐱"),
                    CartaJogo(id = 7, simbolo = "🦊")
                ),
                tentativas = 2,
                segundos = 15
            ),
            onCartaClicada = {},
            onNomeParaSalvarChange = {},
            onSalvarPartida = {},
            onReiniciar = {},
            onVoltar = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogoVitoriaPreview() {
    MaterialTheme {
        DialogoVitoria(
            uiState = JogoUiState(venceu = true, pontuacao = 920, nomeParaSalvar = "João"),
            onNomeParaSalvarChange = {},
            onSalvarPartida = {},
            onReiniciar = {},
            onVoltar = {}
        )
    }
}