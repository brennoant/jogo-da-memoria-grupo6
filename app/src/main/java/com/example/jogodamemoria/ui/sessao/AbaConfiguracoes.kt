package com.example.jogodamemoria.ui.sessao

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

@Composable
fun AbaConfiguracoes(
    viewModel: SessaoViewModel,
    onTrocarPerfil: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("⚙️ Configurações", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.nomePadrao,
            onValueChange = { viewModel.onNomePadraoChange(it) },
            label = { Text("Nome padrão do jogador") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Dificuldade padrão", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Dificuldades.TODAS.forEach { dificuldade ->
                FilterChip(
                    selected = uiState.dificuldadePadrao == dificuldade,
                    onClick = { viewModel.onDificuldadePadraoChange(dificuldade) },
                    label = { Text(Dificuldades.nomeExibicao(dificuldade)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AbaConfiguracoesPreview() {
    MaterialTheme {
        AbaConfiguracoes(viewModel = viewModel(), onTrocarPerfil = {})
    }
}
