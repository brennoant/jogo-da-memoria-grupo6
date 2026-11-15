package com.example.jogodamemoria.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartaDAO {
    @Insert
    suspend fun inserir(carta: Carta)

    @Insert
    suspend fun inserirTodas(cartas: List<Carta>)

    @Update
    suspend fun atualizar(carta: Carta)

    @Delete
    suspend fun deletar(carta: Carta)

    @Query("DELETE FROM cartas")
    suspend fun deletarTodas()

    @Query("SELECT COUNT(*) FROM cartas")
    suspend fun contar(): Int

    @Query("SELECT * FROM cartas ORDER BY tema ASC, nome ASC")
    fun buscarTodas(): Flow<List<Carta>>
}
