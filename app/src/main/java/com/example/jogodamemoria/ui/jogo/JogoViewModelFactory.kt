package com.example.jogodamemoria.ui.jogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jogodamemoria.data.repository.CartasRepository
import com.example.jogodamemoria.data.repository.PartidasRepository

class JogoViewModelFactory(
    private val cartasRepository: CartasRepository,
    private val partidasRepository: PartidasRepository,
    private val tema: String,
    private val dificuldade: String,
    private val nomePadrao: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != JogoViewModel::class.java) {
            throw IllegalArgumentException(
                "Esta Factory cria apenas JogoViewModel."
            )
        }

        return JogoViewModel(
            cartasRepository = cartasRepository,
            partidasRepository = partidasRepository,
            tema = tema,
            dificuldade = dificuldade,
            nomePadrao = nomePadrao
        ) as T
    }
}