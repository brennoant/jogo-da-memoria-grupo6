package com.example.jogodamemoria.data.repository

import com.example.jogodamemoria.data.local.Carta
import com.example.jogodamemoria.data.local.CartaDAO
import com.example.jogodamemoria.data.local.CartasPadrao
import com.example.jogodamemoria.data.remote.FirebaseCartasService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CartasRepository(
    private val cartaDao: CartaDAO,
    private val firebase: FirebaseCartasService
) {
    fun buscarTodas(): Flow<List<Carta>> = cartaDao.buscarTodas()

    suspend fun inserir(carta: Carta): String? {
        return try {
            val idRemoto = firebase.adicionar(carta)
            cartaDao.inserir(carta.copy(idRemoto = idRemoto))
            null
        } catch (e: Exception) {
            "Erro ao salvar carta: ${e.message}"
        }
    }

    suspend fun atualizar(carta: Carta): String? {
        return try {
            if (carta.idRemoto.isEmpty()) {
                val idRemoto = firebase.adicionar(carta)
                cartaDao.atualizar(carta.copy(idRemoto = idRemoto))
            } else {
                firebase.atualizar(carta)
                cartaDao.atualizar(carta)
            }
            null
        } catch (e: Exception) {
            "Erro ao atualizar carta: ${e.message}"
        }
    }

    suspend fun deletar(carta: Carta): String? {
        return try {
            if (carta.idRemoto.isNotEmpty()) {
                firebase.excluir(carta)
            }
            cartaDao.deletar(carta)
            null
        } catch (e: Exception) {
            "Erro ao excluir carta: ${e.message}"
        }
    }

    suspend fun sincronizar(): String? {
        return try {
            var cartasNuvem = firebase.buscarTodas()

            if (cartasNuvem.isEmpty()) {
                val cartasLocais = cartaDao.buscarTodas().first()
                for (carta in cartasLocais) {
                    firebase.adicionar(carta)
                }
                cartasNuvem = firebase.buscarTodas()
            }

            cartaDao.deletarTodas()
            cartaDao.inserirTodas(cartasNuvem)
            null
        } catch (e: Exception) {
            "Erro ao sincronizar: ${e.message}"
        }
    }

    suspend fun garantirCartasPadrao(): String? {
        return try {
            if (cartaDao.contar() == 0) {
                cartaDao.inserirTodas(CartasPadrao.LISTA)
            }
            null
        } catch (e: Exception) {
            "Erro ao carregar cartas padrão: ${e.message}"
        }
    }
}