package com.example.jogodamemoria.ui.sessao

// ╔══════════════════════════════════════════════╗
// ║  SessaoViewModel.kt                          ║
// ║  Guarda o perfil (Jogador ou Admin) e as     ║
// ║  configurações. Criado UMA vez no            ║
// ║  AppNavigation e compartilhado entre telas   ║
// ║  (igual ao MeuViewModel da Aula 13)          ║
// ╚══════════════════════════════════════════════╝

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Não tem Factory porque não recebe nada no construtor (não usa banco)
class SessaoViewModel : ViewModel() {

    // Estado privado: só o ViewModel pode alterar
    private val _uiState = MutableStateFlow(SessaoUiState())

    // Estado público: as telas só leem
    val uiState = _uiState.asStateFlow()

    // Tela Inicial: guarda qual botão foi clicado (Perfis.JOGADOR ou Perfis.ADMIN)
    fun onEscolherPerfil(perfil: String) {
        _uiState.update { it.copy(perfil = perfil) }
    }

    // Aba Configurações: a cada letra digitada, o nome padrão é atualizado
    fun onNomePadraoChange(nome: String) {
        _uiState.update { it.copy(nomePadrao = nome) }
    }

    // Aba Configurações: troca a dificuldade padrão (Dificuldades.FACIL, MEDIO ou DIFICIL)
    fun onDificuldadePadraoChange(dificuldade: String) {
        _uiState.update { it.copy(dificuldadePadrao = dificuldade) }
    }

    // Botão "Trocar perfil": apaga o perfil; o nome e a dificuldade continuam salvos
    fun onTrocarPerfil() {
        _uiState.update { it.copy(perfil = "") }
    }
}
