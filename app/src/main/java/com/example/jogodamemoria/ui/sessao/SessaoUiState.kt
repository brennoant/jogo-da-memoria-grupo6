package com.example.jogodamemoria.ui.sessao

// ╔══════════════════════════════════════════════╗
// ║  SessaoUiState.kt                            ║
// ║  Tudo o que a sessão precisa lembrar:        ║
// ║  perfil escolhido, nome e dificuldade padrão ║
// ╚══════════════════════════════════════════════╝

import com.example.jogodamemoria.Dificuldades

data class SessaoUiState(
    // "" = ninguém escolheu ainda; depois vira Perfis.JOGADOR ou Perfis.ADMIN
    val perfil: String = "",

    // Nome que já aparece preenchido no diálogo de vitória do jogo
    val nomePadrao: String = "Jogador",

    // Dificuldade que já vem marcada na aba Jogar
    val dificuldadePadrao: String = Dificuldades.FACIL
)
