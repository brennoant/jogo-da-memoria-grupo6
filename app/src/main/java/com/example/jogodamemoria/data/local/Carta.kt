package com.example.jogodamemoria.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey


@Entity(tableName = "cartas")
data class Carta(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val simbolo: String,
    val nome: String,
    val tema: String,
    val idRemoto: String = ""
)
