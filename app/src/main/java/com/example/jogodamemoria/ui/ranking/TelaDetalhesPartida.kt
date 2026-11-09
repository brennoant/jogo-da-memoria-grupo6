package com.example.jogodamemoria.ui.ranking

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.data.local.AppDatabase
import com.example.jogodamemoria.data.repository.PartidasRepository
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Assinatura fixa do contrato
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDetalhesPartida(
    id: Int,
    onVoltar: () -> Unit,
    viewModel: RankingViewModel = viewModel(
        factory = RankingViewModelFactory(
            PartidasRepository(AppDatabase.getDatabase(LocalContext.current).partidaDAO())
        )
    )
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Busca a partida específica na lista completa (todasPartidas)
    val partida = state.todasPartidas.find { it.id == id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Partida") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Text("←") // Ícone simples de voltar
                    }
                }
            )
        }
    ) { paddingValues ->
        if (partida == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Partida não encontrada")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Campo de edição do nome
                OutlinedTextField(
                    value = state.nomeEmEdicao.ifEmpty { partida.nomeJogador },
                    onValueChange = { viewModel.onNomeEdicaoChange(it) },
                    label = { Text("Nome do Jogador") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.onSalvarNome(partida)
                        onVoltar() // Volta para o ranking após salvar
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salvar Nome")
                }

                Divider()

                // Detalhes estatísticos
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Tema: ${partida.tema}", style = MaterialTheme.typography.bodyLarge)
                        Text("Dificuldade: ${Dificuldades.nomeExibicao(partida.dificuldade)}")
                        Text("Pontuação: ${partida.pontuacao} pts")
                        Text("Tempo: ${partida.tempoSegundos} segundos")
                        Text("Tentativas: ${partida.tentativas}")

                        // Formata a data (Long) para string legível
                        val dataFormatada = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            .format(Date(partida.data))
                        Text("Data: $dataFormatada")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botão de exclusão
                Button(
                    onClick = {
                        viewModel.onExcluir(partida)
                        onVoltar() // Volta para o ranking após excluir
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Excluir Partida")
                }

                // Mensagem de erro
                state.mensagemErro?.let { erro ->
                    Text(erro, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TelaDetalhesPartidaPreview() {
    MaterialTheme {
        // No preview, passamos um ID que não existe ou tratamos o null
        TelaDetalhesPartida(id = 0, onVoltar = {})
    }
}
