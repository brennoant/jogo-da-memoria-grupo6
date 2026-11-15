package com.example.jogodamemoria

object Perfis {
    const val JOGADOR = "JOGADOR"
    const val ADMIN   = "ADMIN"
}

object Temas {
    const val ANIMAIS  = "Animais"
    const val FRUTAS   = "Frutas"
    const val ESPORTES = "Esportes"
    val TODOS = listOf(ANIMAIS, FRUTAS, ESPORTES)
}

object Dificuldades {
    const val FACIL   = "FACIL"
    const val MEDIO   = "MEDIO"
    const val DIFICIL = "DIFICIL"
    val TODAS = listOf(FACIL, MEDIO, DIFICIL)

    fun pares(dificuldade: String): Int = when (dificuldade) {
        MEDIO -> 6
        DIFICIL -> 8
        else -> 4
    }

    fun multiplicador(dificuldade: String): Int = when (dificuldade) {
        MEDIO -> 2
        DIFICIL -> 3
        else -> 1
    }

    fun nomeExibicao(dificuldade: String): String = when (dificuldade) {
        MEDIO -> "Médio"
        DIFICIL -> "Difícil"
        else -> "Fácil"
    }
}
