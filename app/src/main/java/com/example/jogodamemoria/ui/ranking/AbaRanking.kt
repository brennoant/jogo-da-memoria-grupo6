package com.example.jogodamemoria.ui.ranking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.Temas
import com.example.jogodamemoria.data.local.AppDatabase
import com.example.jogodamemoria.data.repository.PartidasRepository
import androidx.compose.ui.tooling.preview.Preview

private val PADDING_PADRAO_16 = 16.dp
private val PADDING_PADRAO_8 = 8.dp

// Assinatura fixa do contrato: recebe apenas o callback de navegação
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbaRanking(
    onAbrirDetalhes: (id: Int) -> Unit,
    viewModel: RankingViewModel = viewModel(
        factory = RankingViewModelFactory(
            PartidasRepository(AppDatabase.getDatabase(LocalContext.current).partidaDAO())
        )
    )
) {
    // Coleta o estado reativo: a tela se redesenha sozinha quando o uiState muda
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(PADDING_PADRAO)) {
        // Linha de filtros e ordenação
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PADDING_PADRAO_8)
        ) {
            // Filtro por Tema
            FilterChip(
                selected = state.filtroTema != null,
                onClick = {
                    val proximo = if (state.filtroTema == null) Temas.ANIMAIS else null
                    viewModel.onFiltroTema(proximo)
                },
                label = { Text(state.filtroTema ?: "Todos os temas") }
            )

            // Filtro por Dificuldade
            FilterChip(
                selected = state.filtroDificuldade != null,
                onClick = {
                    val proximo = if (state.filtroDificuldade == null) Dificuldades.FACIL else null
                    viewModel.onFiltroDificuldade(proximo)
                },
                label = {
                    Text(
                        state.filtroDificuldade?.let { Dificuldades.nomeExibicao(it) }
                            ?: "Todas as dificuldades"
                    )
                }
            )

            // Ordenação
            FilterChip(
                selected = true,
                onClick = {
                    val proximo = when (state.ordenacao) {
                        "PONTOS" -> "TEMPO"
                        "TEMPO" -> "DATA"
                        else -> "PONTOS"
                    }
                    viewModel.onOrdenacao(proximo)
                },
                label = {
                    Text(
                        when (state.ordenacao) {
                            "TEMPO" -> "Por tempo"
                            "DATA" -> "Por data"
                            else -> "Por pontos"
                        }
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(PADDING_PADRAO))

        // Lista de partidas
        if (state.partidasExibidas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhuma partida encontrada", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(PADDING_PADRAO_8)) {
                items(state.partidasExibidas, key = { it.id }) { partida ->
                    Card(
                        onClick = { onAbrirDetalhes(partida.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(PADDING_PADRAO)) {
                            Text(
                                text = "${partida.nomeJogador} — ${partida.pontuacao} pts",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "${partida.tema} · ${Dificuldades.nomeExibicao(partida.dificuldade)} · ${partida.tempoSegundos}s",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        // Mensagem de erro (se houver)
        state.mensagemErro?.let { erro ->
            Spacer(modifier = Modifier.height(PADDING_PADRAO_8))
            Text(erro, color = MaterialTheme.colorScheme.error)
        }
    }
}

// Preview com dados falsos (não depende do banco real)
@Preview(showBackground = true)
@Composable
private fun AbaRankingPreview() {
    MaterialTheme {
        AbaRanking(onAbrirDetalhes = {})
    }
}
