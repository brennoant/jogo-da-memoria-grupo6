package com.example.jogodamemoria.ui.sessao

// ╔══════════════════════════════════════════════╗
// ║  AbaConfiguracoes.kt                         ║
// ║  ABA 3 do Jogador: nome padrão, dificuldade  ║
// ║  padrão e botão Trocar perfil                ║
// ║  Lê e altera o SessaoViewModel compartilhado ║
// ╚══════════════════════════════════════════════╝

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jogodamemoria.Dificuldades

// O viewModel NÃO tem valor padrão: ele vem do AppNavigation,
// para ser o mesmo objeto que a Tela Inicial e o Jogo usam.
@Composable
fun AbaConfiguracoes(
    viewModel: SessaoViewModel,
    onTrocarPerfil: () -> Unit
) {
    // Lê o estado; quando ele muda, a tela se redesenha sozinha
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("⚙️ Configurações", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // ------ NOME PADRÃO ------
        // O texto vem do uiState e cada letra digitada vai para o ViewModel
        OutlinedTextField(
            value = uiState.nomePadrao,
            onValueChange = { viewModel.onNomePadraoChange(it) },
            label = { Text("Nome padrão do jogador") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ------ DIFICULDADE PADRÃO ------
        Text("Dificuldade padrão", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Um FilterChip para cada dificuldade de Dificuldades.TODAS
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Dificuldades.TODAS.forEach { dificuldade ->
                FilterChip(
                    selected = uiState.dificuldadePadrao == dificuldade,
                    onClick = { viewModel.onDificuldadePadraoChange(dificuldade) },
                    // Na tela aparece "Fácil"; no estado fica guardado "FACIL"
                    label = { Text(Dificuldades.nomeExibicao(dificuldade)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ------ TROCAR PERFIL ------
        // Primeiro apaga o perfil no ViewModel, depois o AppNavigation volta para a Tela Inicial
        Button(
            onClick = {
                viewModel.onTrocarPerfil()
                onTrocarPerfil()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Trocar perfil")
        }
    }
}

// ── Preview ──────────────────────────────────────
// SessaoViewModel não precisa de Factory, então viewModel() já cria um para o Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AbaConfiguracoesPreview() {
    MaterialTheme {
        AbaConfiguracoes(viewModel = viewModel(), onTrocarPerfil = {})
    }
}
