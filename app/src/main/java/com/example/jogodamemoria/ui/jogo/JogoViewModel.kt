package com.example.jogodamemoria.ui.jogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jogodamemoria.Dificuldades
import com.example.jogodamemoria.data.local.Partida
import com.example.jogodamemoria.data.repository.CartasRepository
import com.example.jogodamemoria.data.repository.PartidasRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JogoViewModel(
    private val cartasRepository: CartasRepository,
    private val partidasRepository: PartidasRepository,
    private val tema: String,
    private val dificuldade: String,
    private val nomePadrao: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        JogoUiState(nomeParaSalvar = nomePadrao)
    )
    val uiState: StateFlow<JogoUiState> = _uiState.asStateFlow()

    private var carregamento: Job? = null
    private var comparacao: Job? = null
    private var cronometro: Job? = null
    private var salvando = false

    init {
        onReiniciar()
    }

    fun onReiniciar() {
        if (salvando) return

        carregamento?.cancel()
        comparacao?.cancel()
        cronometro?.cancel()

        _uiState.value = JogoUiState(nomeParaSalvar = nomePadrao)
        carregamento = viewModelScope.launch {
            montarTabuleiro()
        }
    }

    private suspend fun montarTabuleiro() {
        try {
            val erroCartasPadrao = cartasRepository.garantirCartasPadrao()
            val cartasDoTema = cartasRepository.buscarTodas().first()
                .filter { it.tema == tema }
                .distinctBy { it.simbolo }
            val quantidadeDePares = Dificuldades.pares(dificuldade)

            if (cartasDoTema.size < quantidadeDePares) {
                val erro = erroCartasPadrao ?: "Cartas insuficientes para esta dificuldade."
                _uiState.update { it.copy(mensagemErro = erro) }
                return
            }

            val cartas = cartasDoTema.shuffled()
                .take(quantidadeDePares)
                .flatMap { listOf(it.simbolo, it.simbolo) }
                .mapIndexed { indice, simbolo ->
                    CartaJogo(id = indice, simbolo = simbolo)
                }
                .shuffled()

            _uiState.update { it.copy(cartas = cartas) }
            iniciarCronometro()
        } catch (erro: CancellationException) {
            throw erro
        } catch (erro: Exception) {
            _uiState.update { it.copy(mensagemErro = "Erro ao carregar as cartas.") }
        }
    }

    private fun iniciarCronometro() {
        cronometro = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { it.copy(segundos = it.segundos + 1) }
            }
        }
    }

    fun onCartaClicada(id: Int) {
        val estado = _uiState.value
        if (estado.venceu) return

        val carta = estado.cartas.find { it.id == id } ?: return
        if (carta.virada || carta.encontrada) return

        val abertas = estado.cartas.filter { it.virada && !it.encontrada }
        if (abertas.size >= 2) return

        val cartasViradas = estado.cartas.map {
            if (it.id == id) it.copy(virada = true) else it
        }
        val primeira = abertas.firstOrNull()

        if (primeira == null) {
            _uiState.update { it.copy(cartas = cartasViradas) }
            return
        }

        val tentativas = estado.tentativas + 1

        if (primeira.simbolo == carta.simbolo) {
            val cartas = cartasViradas.map {
                if (it.id == primeira.id || it.id == id) {
                    it.copy(encontrada = true)
                } else {
                    it
                }
            }
            val venceu = cartas.all { it.encontrada }
            val pontuacao = if (venceu) {
                (1000 - tentativas * 10 - estado.segundos * 2).coerceAtLeast(0) *
                    Dificuldades.multiplicador(dificuldade)
            } else {
                0
            }

            if (venceu) cronometro?.cancel()

            _uiState.update {
                it.copy(
                    cartas = cartas,
                    tentativas = tentativas,
                    venceu = venceu,
                    pontuacao = pontuacao
                )
            }
        } else {
            _uiState.update {
                it.copy(cartas = cartasViradas, tentativas = tentativas)
            }

            comparacao = viewModelScope.launch {
                delay(1000)
                _uiState.update { atual ->
                    atual.copy(
                        cartas = atual.cartas.map {
                            if (it.id == primeira.id || it.id == id) {
                                it.copy(virada = false)
                            } else {
                                it
                            }
                        }
                    )
                }
            }
        }
    }

    fun onNomeParaSalvarChange(nome: String) {
        if (salvando || _uiState.value.partidaSalva) return

        _uiState.update {
            it.copy(nomeParaSalvar = nome, mensagemErro = null)
        }
    }

    fun onSalvarPartida() {
        val estado = _uiState.value
        if (!estado.venceu || estado.partidaSalva || salvando) return

        val nome = estado.nomeParaSalvar.trim()
        if (nome.isEmpty()) {
            _uiState.update { it.copy(mensagemErro = "Digite o nome do jogador.") }
            return
        }

        val partida = Partida(
            nomeJogador = nome,
            tema = tema,
            dificuldade = dificuldade,
            tentativas = estado.tentativas,
            tempoSegundos = estado.segundos,
            pontuacao = estado.pontuacao,
            data = System.currentTimeMillis()
        )

        salvando = true
        _uiState.update { it.copy(mensagemErro = null) }

        viewModelScope.launch {
            try {
                val erro = partidasRepository.inserir(partida)
                _uiState.update {
                    it.copy(partidaSalva = erro == null, mensagemErro = erro)
                }
            } finally {
                salvando = false
            }
        }
    }
}