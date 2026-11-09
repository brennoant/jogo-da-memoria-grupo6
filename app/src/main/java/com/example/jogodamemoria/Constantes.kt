package com.example.jogodamemoria

// Valores fixos usados pelo app inteiro. Ninguém digita "FACIL" ou "Animais" solto no código:
// todo mundo usa estas constantes, assim não existe um "Facil" de um jeito e "Fácil" de outro.

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

    // Quantos pares de cartas cada dificuldade usa
    fun pares(dificuldade: String): Int = when (dificuldade) {
        MEDIO -> 6
        DIFICIL -> 8
        else -> 4
    }

    // Multiplicador da pontuação
    fun multiplicador(dificuldade: String): Int = when (dificuldade) {
        MEDIO -> 2
        DIFICIL -> 3
        else -> 1
    }

    // Texto com acento que aparece na tela (o valor sem acento é o que viaja na rota)
    fun nomeExibicao(dificuldade: String): String = when (dificuldade) {
        MEDIO -> "Médio"
        DIFICIL -> "Difícil"
        else -> "Fácil"
    }
}
