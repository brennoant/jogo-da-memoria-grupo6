package com.example.jogodamemoria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jogodamemoria.ui.theme.JogoDaMemoriaTheme

// Ponto de entrada do app. Por enquanto só mostra um texto;
// no próximo commit passa a abrir o AppNavigation.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JogoDaMemoriaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("Jogo da Memória")
                    }
                }
            }
        }
    }
}
