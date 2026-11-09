package com.example.jogodamemoria.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

// Uma carta do jogo. Cada objeto Carta vira uma linha da tabela "cartas" no banco.
@Entity(tableName = "cartas")
data class Carta(
    // autoGenerate = true: o banco escolhe o id sozinho (1, 2, 3...). O 0 é só "ainda não tem id".
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val simbolo: String,      // emoji, ex.: "🐶"
    val nome: String,         // ex.: "Cachorro"
    val tema: String,         // sempre um valor de Temas
    val idRemoto: String = "" // id do documento no Firestore
)
