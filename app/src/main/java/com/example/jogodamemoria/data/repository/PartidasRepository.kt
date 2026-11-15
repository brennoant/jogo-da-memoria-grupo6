package com.example.jogodamemoria.data.repository

import com.example.jogodamemoria.data.local.Partida
import com.example.jogodamemoria.data.local.PartidaDAO
import kotlinx.coroutines.flow.Flow

class PartidasRepository(private val partidaDao: PartidaDAO) {

    fun buscarTodas(): Flow<List<Partida>> = partidaDao.buscarTodas()

    suspend fun inserir(partida: Partida): String? {
        return try {
            partidaDao.inserir(partida)
            null
        } catch (e: Exception) {
            "Erro ao salvar partida: ${e.message}"
        }
    }

    suspend fun atualizar(partida: Partida): String? {
        return try {
            partidaDao.atualizar(partida)
            null
        } catch (e: Exception) {
            "Erro ao atualizar partida: ${e.message}"
        }
    }

    suspend fun deletar(partida: Partida): String? {
        return try {
            partidaDao.deletar(partida)
            null
        } catch (e: Exception) {
            "Erro ao excluir partida: ${e.message}"
        }
    }
}
