package com.example.jogodamemoria.ui.jogo

data class CartaJogo(
    val id: Int,
    val simbolo: String,
    val virada: Boolean = false,
    val encontrada: Boolean = false
)

data class JogoUiState(
    val cartas: List<CartaJogo> = emptyList(),
    val tentativas: Int = 0,
    val segundos: Int = 0,
    val venceu: Boolean = false,
    val pontuacao: Int = 0,
    val nomeParaSalvar: String = "",
    val partidaSalva: Boolean = false,
    val mensagemErro: String? = null
)