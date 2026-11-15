package com.example.jogodamemoria.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "partidas")
data class Partida(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeJogador: String,
    val tema: String,
    val dificuldade: String,
    val tentativas: Int,
    val tempoSegundos: Int,
    val pontuacao: Int,
    val data: Long
)
