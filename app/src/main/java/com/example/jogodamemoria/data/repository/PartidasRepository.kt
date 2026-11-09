package com.example.jogodamemoria.data.repository

import com.example.jogodamemoria.data.local.Partida
import com.example.jogodamemoria.data.local.PartidaDAO
import kotlinx.coroutines.flow.Flow

// Fica entre o ViewModel e o DAO: o ViewModel nunca fala direto com o banco.
// Aqui é o lugar onde os erros do banco são capturados.
class PartidasRepository(private val partidaDao: PartidaDAO) {

    // Só repassa o Flow. Não precisa de try-catch: não é uma operação única, é a lista "ao vivo".
    fun buscarTodas(): Flow<List<Partida>> = partidaDao.buscarTodas()

    // Devolve null se deu certo, ou o texto do erro se falhou
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
            "Erro ao salvar partida: ${e.message}"
        }
    }

    suspend fun deletar(partida: Partida): String? {
        return try {
            partidaDao.deletar(partida)
            null
        } catch (e: Exception) {
            "Erro ao salvar partida: ${e.message}"
        }
    }
}
