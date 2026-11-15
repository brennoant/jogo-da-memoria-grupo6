package com.example.jogodamemoria.ui.sessao

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SessaoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SessaoUiState())

    val uiState = _uiState.asStateFlow()

    fun onEscolherPerfil(perfil: String) {
        _uiState.update { it.copy(perfil = perfil) }
    }

    fun onNomePadraoChange(nome: String) {
        _uiState.update { it.copy(nomePadrao = nome) }
    }

    fun onDificuldadePadraoChange(dificuldade: String) {
        _uiState.update { it.copy(dificuldadePadrao = dificuldade) }
    }

    fun onTrocarPerfil() {
        _uiState.update { it.copy(perfil = "") }
    }
}
