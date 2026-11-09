package com.example.jogodamemoria.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

// DAO = Data Access Object: a lista de operações que dá para fazer na tabela "cartas".
// Só escrevemos a assinatura; o Room gera o código de verdade na compilação (pelo KSP).
@Dao
interface CartaDAO {
    // suspend: roda dentro de uma coroutine, sem travar a tela enquanto o banco trabalha
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

    // Flow: não é uma resposta única, é um "cano" que manda a lista de novo sempre que a tabela muda
    @Query("SELECT * FROM cartas ORDER BY tema ASC, nome ASC")
    fun buscarTodas(): Flow<List<Carta>>
}
