package com.example.jogodamemoria.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

// Operações da tabela "partidas"
@Dao
interface PartidaDAO {
    @Insert
    suspend fun inserir(partida: Partida)

    @Update
    suspend fun atualizar(partida: Partida)

    @Delete
    suspend fun deletar(partida: Partida)

    // Maior pontuação primeiro; no empate, ganha quem foi mais rápido e depois quem errou menos
    @Query("SELECT * FROM partidas ORDER BY pontuacao DESC, tempoSegundos ASC, tentativas ASC")
    fun buscarTodas(): Flow<List<Partida>>
}
