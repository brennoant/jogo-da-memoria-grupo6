package com.example.jogodamemoria.ui.sessao

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TelaInicial(onJogador: () -> Unit, onAdmin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🧠", style = MaterialTheme.typography.displayLarge)
        Text("Jogo da Memória", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(8.dp))
        Text("Escolha como você quer entrar")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onJogador,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text("Jogador", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onAdmin,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text("Admin", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaInicialPreview() {
    MaterialTheme {
        TelaInicial(onJogador = {}, onAdmin = {})
    }
}
