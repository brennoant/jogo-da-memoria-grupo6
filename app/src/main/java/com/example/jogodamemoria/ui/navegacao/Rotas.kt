package com.example.jogodamemoria.ui.navegacao

object Rotas {
    const val INICIAL  = "inicial"
    const val JOGADOR  = "jogador"
    const val JOGO     = "jogo/{tema}/{dificuldade}"
    const val DETALHES = "detalhes/{id}"
    const val ADMIN    = "admin"

    fun jogo(tema: String, dificuldade: String) = "jogo/$tema/$dificuldade"
    fun detalhes(id: Int) = "detalhes/$id"
}

object RotasAbas {
    const val ABA_JOGAR   = "aba_jogar"
    const val ABA_RANKING = "aba_ranking"
    const val ABA_CONFIG  = "aba_config"
}
