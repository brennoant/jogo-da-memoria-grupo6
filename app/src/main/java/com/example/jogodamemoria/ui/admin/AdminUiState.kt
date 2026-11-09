package com.example.jogodamemoria.ui.admin

import com.example.jogodamemoria.Temas
import com.example.jogodamemoria.data.local.Carta

data class AdminUiState(
    val cartas: List<Carta> = emptyList(),
    val cartasFiltradas: List<Carta> = emptyList(),
    val simbolo: String = "",
    val nome: String = "",
    val tema: String = Temas.ANIMAIS,
    val cartaEmEdicao: Carta? = null,
    val filtroTema: String? = null,
    val carregando: Boolean = false,
    val mensagemErro: String? = null
)