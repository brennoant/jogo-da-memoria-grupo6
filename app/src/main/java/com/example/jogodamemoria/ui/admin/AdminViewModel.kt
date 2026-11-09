package com.example.jogodamemoria.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jogodamemoria.data.local.Carta
import com.example.jogodamemoria.data.repository.CartasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: CartasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val erro = repository.garantirCartasPadrao()
            _uiState.update { it.copy(mensagemErro = erro) }
        }
        viewModelScope.launch {
            repository.buscarTodas().collect { cartas ->
                _uiState.update {
                    it.copy(
                        cartas = cartas,
                        cartasFiltradas = filtrar(cartas, it.filtroTema)
                    )
                }
            }
        }
    }

    fun onSimboloChange(novoSimbolo: String) {
        _uiState.update { it.copy(simbolo = novoSimbolo) }
    }

    fun onNomeChange(novoNome: String) {
        _uiState.update { it.copy(nome = novoNome) }
    }

    fun onTemaChange(novoTema: String) {
        _uiState.update { it.copy(tema = novoTema) }
    }

    fun onEdit(carta: Carta) {
        _uiState.update {
            it.copy(
                cartaEmEdicao = carta,
                simbolo = carta.simbolo,
                nome = carta.nome,
                tema = carta.tema
            )
        }
    }

    fun onDelete(carta: Carta) {
        viewModelScope.launch {
            _uiState.update { it.copy(carregando = true) }
            val erro = repository.deletar(carta)
            _uiState.update { it.copy(carregando = false, mensagemErro = erro) }
        }
    }

    fun onSave() {
        val state = _uiState.value

        if (state.simbolo.isBlank() || state.nome.isBlank()) {
            _uiState.update { it.copy(mensagemErro = "Preencha o símbolo e o nome") }
            return
        }
        val cartaParaSalvar = state.cartaEmEdicao?.copy(
            simbolo = state.simbolo,
            nome = state.nome,
            tema = state.tema
        ) ?: Carta(simbolo = state.simbolo, nome = state.nome, tema = state.tema)

        viewModelScope.launch {
            _uiState.update { it.copy(carregando = true) }

            val erro = if (state.cartaEmEdicao == null) {
                repository.inserir(cartaParaSalvar)
            } else {
                repository.atualizar(cartaParaSalvar)
            }

            if (erro == null) limparCampos()
            _uiState.update { it.copy(carregando = false, mensagemErro = erro) }
        }
    }

    fun onFiltroTema(tema: String?) {
        _uiState.update {
            it.copy(
                filtroTema = tema,
                cartasFiltradas = filtrar(it.cartas, tema)
            )
        }
    }

    fun onSincronizar() {
        viewModelScope.launch {
            _uiState.update { it.copy(carregando = true) }
            val erro = repository.sincronizar()
            _uiState.update { it.copy(carregando = false, mensagemErro = erro) }
        }
    }

    private fun filtrar(cartas: List<Carta>, tema: String?): List<Carta> {
        return if (tema == null) cartas else cartas.filter { it.tema == tema }
    }

    private fun limparCampos() {
        _uiState.update {
            it.copy(simbolo = "", nome = "", cartaEmEdicao = null)
        }
    }
}