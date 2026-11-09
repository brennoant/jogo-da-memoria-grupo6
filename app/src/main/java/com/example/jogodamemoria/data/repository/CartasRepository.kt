package com.example.jogodamemoria.data.repository

import com.example.jogodamemoria.data.local.Carta
import com.example.jogodamemoria.data.local.CartaDAO
import com.example.jogodamemoria.data.local.CartasPadrao
import com.example.jogodamemoria.data.remote.FirebaseCartasService
import kotlinx.coroutines.flow.Flow

class CartasRepository (
private val cartaDao: CartaDAO,
private val firebase: FirebaseCartasService
){
    fun buscarTodas(): Flow<List<Carta>> = cartaDao.buscarTodas()

    suspend fun inserir(carta: Carta): String? {
        return try {
            cartaDao.inserir(carta)
            null
        } catch (e: Exception) {
            "Erro ao salvar carta: ${e.message}"
        }
    }

    suspend fun atualizar(carta: Carta): String? {
        return try {
            cartaDao.atualizar(carta)
            null
        } catch (e: Exception) {
            "Erro ao atualizar carta: ${e.message}"
        }
    }

    suspend fun deletar(carta: Carta): String? {
        return try {
            cartaDao.deletar(carta)
            null
        } catch (e: Exception) {
            "Erro ao excluir carta: ${e.message}"
        }
    }

    suspend fun sincronizar(): String? {
        return null
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