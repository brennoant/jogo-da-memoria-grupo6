package com.example.jogodamemoria.ui.navegacao

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavBar(navInterno: NavHostController) {
    val backStackEntry by navInterno.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    fun abrirAba(rota: String) {
        navInterno.navigate(rota) {
            popUpTo(RotasAbas.ABA_JOGAR)
            launchSingleTop = true
        }
    }

    NavigationBar {
        NavigationBarItem(
            selected = rotaAtual == RotasAbas.ABA_JOGAR,
            onClick  = { abrirAba(RotasAbas.ABA_JOGAR) },
            icon     = { Icon(Icons.Default.PlayArrow, contentDescription = "Jogar") },
            label    = { Text("Jogar") }
        )

        NavigationBarItem(
            selected = rotaAtual == RotasAbas.ABA_RANKING,
            onClick  = { abrirAba(RotasAbas.ABA_RANKING) },
            icon     = { Icon(Icons.Default.Star, contentDescription = "Ranking") },
            label    = { Text("Ranking") }
        )

        NavigationBarItem(
            selected = rotaAtual == RotasAbas.ABA_CONFIG,
            onClick  = { abrirAba(RotasAbas.ABA_CONFIG) },
            icon     = { Icon(Icons.Default.Settings, contentDescription = "Configurações") },
            label    = { Text("Configurações") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    MaterialTheme {
        BottomNavBar(navInterno = rememberNavController())
    }
}
