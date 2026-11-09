package com.example.jogodamemoria.data.remote

import com.example.jogodamemoria.data.local.Carta
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class FirebaseCartasService {

    private val colecao = FirebaseFirestore.getInstance().collection("cartas")

    private val tempoLimite = 10_000L

    suspend fun buscarTodas(): List<Carta> {
        val resultado = withTimeout(tempoLimite) {
            colecao.get(Source.SERVER).await()
        }
        return resultado.documents.map { documento ->
            Carta(
                simbolo = documento.getString("simbolo") ?: "",
                nome = documento.getString("nome") ?: "",
                tema = documento.getString("tema") ?: "",
                idRemoto = documento.id
            )
        }
    }

    suspend fun adicionar(carta: Carta): String {
        val documento = withTimeout(tempoLimite) {
            colecao.add(paraMapa(carta)).await()
        }
        return documento.id
    }

    suspend fun atualizar(carta: Carta) {
        withTimeout(tempoLimite) {
            colecao.document(carta.idRemoto).set(paraMapa(carta)).await()
        }
    }

    suspend fun excluir(carta: Carta) {
        withTimeout(tempoLimite) {
            colecao.document(carta.idRemoto).delete().await()
        }
    }

    private fun paraMapa(carta: Carta) = mapOf(
        "simbolo" to carta.simbolo,
        "nome" to carta.nome,
        "tema" to carta.tema
    )
}