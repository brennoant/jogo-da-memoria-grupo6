package com.example.jogodamemoria.ui.jogar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.Temas
import com.example.jogodamemoria.ui.sessao.SessaoViewModel

@Composable
fun AbaJogar(
    viewModel: SessaoViewModel,
    onJogar: (tema: String, dificuldade: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var tema by rememberSaveable { mutableStateOf(Temas.ANIMAIS) }
    var dificuldade by rememberSaveable(uiState.dificuldadePadrao) {
        mutableStateOf(uiState.dificuldadePadrao)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("🎮 Jogar", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Tema", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Temas.TODOS.forEach { opcao ->
                FilterChip(
                    selected = tema == opcao,
                    onClick = { tema = opcao },
                    label = { Text(opcao) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Dificuldade", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Dificuldades.TODAS.forEach { opcao ->
                FilterChip(
                    selected = dificuldade == opcao,
                    onClick = { dificuldade = opcao },
                    label = { Text(Dificuldades.nomeExibicao(opcao)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("${Dificuldades.pares(dificuldade)} pares de cartas")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onJogar(tema, dificuldade) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text("Começar partida", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AbaJogarPreview() {
    MaterialTheme {
        AbaJogar(viewModel = viewModel(), onJogar = { _, _ -> })
    }
}
