package com.example.jogodamemoria.ui.navegacao

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.Perfis
import com.example.jogodamemoria.Temas
import com.example.jogodamemoria.ui.admin.TelaAdmin
import com.example.jogodamemoria.ui.jogo.TelaJogo
import com.example.jogodamemoria.ui.ranking.TelaDetalhesPartida
import com.example.jogodamemoria.ui.sessao.SessaoViewModel
import com.example.jogodamemoria.ui.sessao.TelaInicial

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val sessao: SessaoViewModel = viewModel()
    val sessaoState by sessao.uiState.collectAsStateWithLifecycle()

    fun trocarPerfil() {
        sessao.onTrocarPerfil()
        navController.navigate(Rotas.INICIAL) {
            popUpTo(Rotas.INICIAL) { inclusive = true }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Rotas.INICIAL,
        modifier = Modifier.safeDrawingPadding()
    ) {
        composable(Rotas.INICIAL) {
            TelaInicial(
                onJogador = {
                    sessao.onEscolherPerfil(Perfis.JOGADOR)
                    navController.navigate(Rotas.JOGADOR)
                },
                onAdmin = {
                    sessao.onEscolherPerfil(Perfis.ADMIN)
                    navController.navigate(Rotas.ADMIN)
                }
            )
        }

        composable(Rotas.JOGADOR) {
            if (sessaoState.perfil == Perfis.JOGADOR) {
                TelaJogador(navController = navController, sessao = sessao)
            } else {
                AvisoPerfil(onVoltar = { trocarPerfil() })
            }
        }

        composable(Rotas.JOGO) { entry ->
            val tema = entry.arguments?.getString("tema") ?: Temas.ANIMAIS
            val dificuldade = entry.arguments?.getString("dificuldade") ?: Dificuldades.FACIL

            TelaJogo(
                tema = tema,
                dificuldade = dificuldade,
                nomePadrao = sessaoState.nomePadrao,
                onVoltar = { navController.popBackStack() }
            )
        }

        composable(Rotas.DETALHES) { entry ->
            val id = entry.arguments?.getString("id")?.toIntOrNull() ?: 0

            TelaDetalhesPartida(
                id = id,
                onVoltar = { navController.popBackStack() }
            )
        }

        composable(Rotas.ADMIN) {
            if (sessaoState.perfil == Perfis.ADMIN) {
                TelaAdmin(onTrocarPerfil = { trocarPerfil() })
            } else {
                AvisoPerfil(onVoltar = { trocarPerfil() })
            }
        }
    }
}

@Composable
fun AvisoPerfil(onVoltar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🔒 Escolha um perfil na tela inicial")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVoltar) {
            Text("Voltar ao início")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AvisoPerfilPreview() {
    MaterialTheme {
        AvisoPerfil(onVoltar = {})
    }
}
