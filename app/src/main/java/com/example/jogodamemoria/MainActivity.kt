package com.example.jogodamemoria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.jogodamemoria.ui.navegacao.AppNavigation
import com.example.jogodamemoria.ui.theme.JogoDaMemoriaTheme

// Primeira coisa que roda quando o app abre
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JogoDaMemoriaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // A partir daqui quem decide qual tela aparece é o AppNavigation
                    AppNavigation()
                }
            }
        }
    }
}
