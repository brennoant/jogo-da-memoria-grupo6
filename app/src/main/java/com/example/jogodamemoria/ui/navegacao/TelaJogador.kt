package com.example.jogodamemoria.ui.navegacao

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jogodamemoria.ui.jogar.AbaJogar
import com.example.jogodamemoria.ui.ranking.AbaRanking
import com.example.jogodamemoria.ui.sessao.AbaConfiguracoes
import com.example.jogodamemoria.ui.sessao.SessaoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaJogador(navController: NavHostController, sessao: SessaoViewModel) {
    val navInterno = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jogo da Memória") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavBar(navInterno = navInterno)
        }
    ) { padding ->

        NavHost(
            navController = navInterno,
            startDestination = RotasAbas.ABA_JOGAR,
            modifier = Modifier.padding(padding)
        ) {
            composable(RotasAbas.ABA_JOGAR) {
                AbaJogar(
                    viewModel = sessao,
                    onJogar = { tema, dificuldade ->
                        navController.navigate(Rotas.jogo(tema, dificuldade))
                    }
                )
            }

            composable(RotasAbas.ABA_RANKING) {
                AbaRanking(
                    onAbrirDetalhes = { id -> navController.navigate(Rotas.detalhes(id)) }
                )
            }

            composable(RotasAbas.ABA_CONFIG) {
                AbaConfiguracoes(
                    viewModel = sessao,
                    onTrocarPerfil = {
                        navController.navigate(Rotas.INICIAL) {
                            popUpTo(Rotas.INICIAL) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaJogadorPreview() {
    MaterialTheme {
        TelaJogador(
            navController = rememberNavController(),
            sessao = viewModel()
        )
    }
}
