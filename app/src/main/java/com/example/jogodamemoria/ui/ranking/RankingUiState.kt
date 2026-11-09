package com.example.jogodamemoria.ui.ranking

import com.example.jogodamemoria.data.local.Partida

data class RankingUiState(
    // Cópia completa vinda do Flow do banco; fonte única de verdade
    val todasPartidas: List<Partida> = emptyList(),

    // Lista já filtrada e ordenada; é esta que a LazyColumn renderiza
    val partidasExibidas: List<Partida> = emptyList(),

    // Filtros ativos (null = todos)
    val filtroTema: String? = null,
    val filtroDificuldade: String? = null,

    // Critério de ordenação atual: "PONTOS", "TEMPO" ou "DATA"
    val ordenacao: String = "PONTOS",

    // Valor temporário do TextField na tela de detalhes
    val nomeEmEdicao: String = "",

    // Erro retornado pelo Repository (null = sem erro)
    val mensagemErro: String? = null
)
