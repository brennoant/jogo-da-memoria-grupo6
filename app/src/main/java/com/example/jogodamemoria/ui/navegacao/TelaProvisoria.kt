package com.example.jogodamemoria.ui.navegacao

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Tela temporária que fica no lugar das telas que ainda não foram feitas.
// Mostra um texto e um botão, para dar para testar a navegação.
@Composable
fun TelaProvisoria(texto: String, textoBotao: String, onBotaoClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = texto)
        Text(text = "Em construção")

        Button(onClick = onBotaoClick) {
            Text(text = textoBotao)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaProvisoriaPreview() {
    TelaProvisoria(texto = "Tela de teste", textoBotao = "Voltar", onBotaoClick = {})
}
