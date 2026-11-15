package com.example.jogodamemoria.ui.sessao

import com.example.jogodamemoria.Dificuldades

data class SessaoUiState(
    val perfil: String = "",

    val nomePadrao: String = "Jogador",

    val dificuldadePadrao: String = Dificuldades.FACIL
)
