package com.example.jogodamemoria.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jogodamemoria.data.local.Partida
import com.example.jogodamemoria.data.repository.PartidasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RankingViewModel(
    private val repository: PartidasRepository
) : ViewModel() {

    // Estado privado: só o ViewModel pode alterar
    private val _uiState = MutableStateFlow(RankingUiState())

    // Estado público: a tela só lê (imutável)
    val uiState = _uiState.asStateFlow()

    init {
        // Coleta o Flow do banco: sempre que uma partida for salva/excluída em outra tela,
        // este bloco roda automaticamente e atualiza a lista
        viewModelScope.launch {
            repository.buscarTodas().collect { partidas ->
                _uiState.update { it.copy(todasPartidas = partidas) }
                aplicarFiltros()
            }
        }
    }

    // Função privada: filtra e ordena a lista exibida
    // Chamada sempre que todasPartidas muda OU quando o usuário altera filtro/ordenação
    private fun aplicarFiltros() {
        val state = _uiState.value

        var lista = state.todasPartidas

        // Filtro por tema (null = todos)
        if (state.filtroTema != null) {
            lista = lista.filter { it.tema == state.filtroTema }
        }

        // Filtro por dificuldade (null = todas)
        if (state.filtroDificuldade != null) {
            lista = lista.filter { it.dificuldade == state.filtroDificuldade }
        }

        // Ordenação conforme critério escolhido
        lista = when (state.ordenacao) {
            "TEMPO" -> lista.sortedBy { it.tempoSegundos }
            "DATA" -> lista.sortedByDescending { it.data }
            else -> lista.sortedByDescending { it.pontuacao } // PONTOS é o padrão
        }

        _uiState.update { it.copy(partidasExibidas = lista) }
    }

    // --- Funções de evento (chamadas pela tela) ---

    // Atualiza o filtro de tema e reprocessa a lista exibida
    fun onFiltroTema(tema: String?) {
        _uiState.update { it.copy(filtroTema = tema) }
        aplicarFiltros()
    }

    // Atualiza o filtro de dificuldade e reprocessa a lista exibida
    fun onFiltroDificuldade(dificuldade: String?) {
        _uiState.update { it.copy(filtroDificuldade = dificuldade) }
        aplicarFiltros()
    }

    // Atualiza o critério de ordenação e reprocessa a lista exibida
    fun onOrdenacao(ordenacao: String) {
        _uiState.update { it.copy(ordenacao = ordenacao) }
        aplicarFiltros()
    }

    // Usado apenas na TelaDetalhesPartida
    fun onNomeEdicaoChange(nome: String) {
        _uiState.update { it.copy(nomeEmEdicao = nome) }
    }

    // Update do CRUD: copia a partida com o novo nome e salva
    fun onSalvarNome(partida: Partida) {
        viewModelScope.launch {
            val erro = repository.atualizar(
                partida.copy(nomeJogador = _uiState.value.nomeEmEdicao)
            )
            _uiState.update { it.copy(mensagemErro = erro) }
        }
    }

    // Delete do CRUD: exclui e limpa mensagem de erro
    fun onExcluir(partida: Partida) {
        viewModelScope.launch {
            val erro = repository.deletar(partida)
            _uiState.update { it.copy(mensagemErro = erro) }
        }
    }
}
