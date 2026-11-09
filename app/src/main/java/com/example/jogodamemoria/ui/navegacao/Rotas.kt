package com.example.jogodamemoria.ui.navegacao

// Endereços das telas. O que está entre { } é um argumento que viaja junto na rota.
object Rotas {
    const val INICIAL  = "inicial"
    const val JOGADOR  = "jogador"
    const val JOGO     = "jogo/{tema}/{dificuldade}"
    const val DETALHES = "detalhes/{id}"
    const val ADMIN    = "admin"

    // Montam a rota já com os valores, ex.: jogo("Animais", "FACIL") -> "jogo/Animais/FACIL"
    fun jogo(tema: String, dificuldade: String) = "jogo/$tema/$dificuldade"
    fun detalhes(id: Int) = "detalhes/$id"
}

// Rotas do NavController interno, que só troca as abas da TelaJogador
object RotasAbas {
    const val ABA_JOGAR   = "aba_jogar"
    const val ABA_RANKING = "aba_ranking"
    const val ABA_CONFIG  = "aba_config"
}
