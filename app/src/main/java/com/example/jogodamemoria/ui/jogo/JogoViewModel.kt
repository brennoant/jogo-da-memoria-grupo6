package com.example.jogodamemoria.ui.jogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.data.repository.CartasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JogoViewModel(
    cartasRepository: CartasRepository,
    tema: String,
    dificuldade: String,
    nomePadrao: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        JogoUiState(nomeParaSalvar = nomePadrao)
    )
    val uiState: StateFlow<JogoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val cartasDoRoom = cartasRepository.buscarTodas().first()
            val cartasDoTema = cartasDoRoom.filter { it.tema == tema }
            val quantidadeDePares = Dificuldades.pares(dificuldade)

            if (cartasDoTema.size < quantidadeDePares) {
                _uiState.update {
                    it.copy(mensagemErro = "Cartas insuficientes para esta dificuldade.")
                }
                return@launch
            }

            val cartas = cartasDoTema.shuffled()
                .take(quantidadeDePares)
                .flatMap { listOf(it.simbolo, it.simbolo) }
                .mapIndexed { indice, simbolo ->
                    CartaJogo(id = indice, simbolo = simbolo)
                }
                .shuffled()

            _uiState.update { it.copy(cartas = cartas) }
        }
    }
}