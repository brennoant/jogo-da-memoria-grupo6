package com.example.jogodamemoria.data.remote

import com.example.jogodamemoria.data.local.Carta

class FirebaseCartasService {

    suspend fun buscarTodas(): List<Carta> {
        return emptyList()
    }

    suspend fun adicionar(carta: Carta): String {
        return ""
    }

    suspend fun atualizar(carta: Carta) {
    }

    suspend fun excluir(carta: Carta) {
    }
}