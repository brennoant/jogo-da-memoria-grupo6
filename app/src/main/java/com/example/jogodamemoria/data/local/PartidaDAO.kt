package com.example.jogodamemoria.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDAO {
    @Insert
    suspend fun inserir(partida: Partida)

    @Update
    suspend fun atualizar(partida: Partida)

    @Delete
    suspend fun deletar(partida: Partida)

    @Query("SELECT * FROM partidas ORDER BY pontuacao DESC, tempoSegundos ASC, tentativas ASC")
    fun buscarTodas(): Flow<List<Partida>>
}
