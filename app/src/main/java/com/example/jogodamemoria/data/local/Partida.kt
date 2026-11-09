package com.example.jogodamemoria.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

// Uma partida já jogada. Cada objeto Partida vira uma linha da tabela "partidas" (usada no ranking).
@Entity(tableName = "partidas")
data class Partida(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeJogador: String,
    val tema: String,         // valor de Temas
    val dificuldade: String,  // valor de Dificuldades
    val tentativas: Int,
    val tempoSegundos: Int,
    val pontuacao: Int,
    val data: Long            // System.currentTimeMillis()
)
