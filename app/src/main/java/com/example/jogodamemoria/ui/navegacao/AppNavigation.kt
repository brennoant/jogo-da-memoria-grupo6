package com.example.jogodamemoria.ui.navegacao

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.Temas

// Liga cada rota a uma tela.
// Por enquanto as telas são provisórias; cada uma vai ser trocada pela tela real.
@Composable
fun AppNavigation() {
    // Controla qual tela está aberta e guarda a pilha de telas (para o botão voltar)
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rotas.INICIAL,
        modifier = Modifier.safeDrawingPadding() // não deixa o conteúdo ficar atrás da barra do celular
    ) {

        // Tela Inicial
        composable(Rotas.INICIAL) {
            TelaProvisoria(
                texto = "Tela Inicial",
                textoBotao = "Entrar como Jogador",
                onBotaoClick = { navController.navigate(Rotas.JOGADOR) }
            )
        }

        // Tela do Jogador (depois vai ter as abas Jogar, Ranking e Configurações)
        composable(Rotas.JOGADOR) {
            TelaProvisoria(
                texto = "Tela do Jogador",
                textoBotao = "Testar o Jogo",
                onBotaoClick = {
                    navController.navigate(Rotas.jogo(Temas.ANIMAIS, Dificuldades.FACIL))
                }
            )
        }

        // Tela do Jogo: recebe o tema e a dificuldade pela rota
        composable(Rotas.JOGO) { entry ->
            val tema = entry.arguments?.getString("tema") ?: Temas.ANIMAIS
            val dificuldade = entry.arguments?.getString("dificuldade") ?: Dificuldades.FACIL

            TelaProvisoria(
                texto = "Jogo - tema: $tema, dificuldade: $dificuldade",
                textoBotao = "Voltar",
                onBotaoClick = { navController.popBackStack() }
            )
        }

        // Tela de Detalhes da Partida: recebe o id da partida pela rota
        composable(Rotas.DETALHES) { entry ->
            val id = entry.arguments?.getString("id")?.toIntOrNull() ?: 0

            TelaProvisoria(
                texto = "Detalhes da partida $id",
                textoBotao = "Voltar",
                onBotaoClick = { navController.popBackStack() }
            )
        }

        // Tela do Admin
        composable(Rotas.ADMIN) {
            TelaProvisoria(
                texto = "Tela do Admin",
                textoBotao = "Voltar",
                onBotaoClick = { navController.popBackStack() }
            )
        }
    }
}
