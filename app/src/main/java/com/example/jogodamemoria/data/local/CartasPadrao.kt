package com.example.jogodamemoria.data.local

import com.example.jogodamemoria.Temas

// Cartas que o app já traz de fábrica: 8 de cada tema (24 no total).
// 8 é o máximo de pares que uma partida usa (Difícil), então qualquer dificuldade dá para jogar.
// Sem id: o banco gera sozinho ao inserir.
object CartasPadrao {
    val LISTA: List<Carta> = listOf(
        Carta(simbolo = "🐶", nome = "Cachorro", tema = Temas.ANIMAIS),
        Carta(simbolo = "🐱", nome = "Gato", tema = Temas.ANIMAIS),
        Carta(simbolo = "🐭", nome = "Rato", tema = Temas.ANIMAIS),
        Carta(simbolo = "🐰", nome = "Coelho", tema = Temas.ANIMAIS),
        Carta(simbolo = "🦊", nome = "Raposa", tema = Temas.ANIMAIS),
        Carta(simbolo = "🐻", nome = "Urso", tema = Temas.ANIMAIS),
        Carta(simbolo = "🐼", nome = "Panda", tema = Temas.ANIMAIS),
        Carta(simbolo = "🦁", nome = "Leão", tema = Temas.ANIMAIS),

        Carta(simbolo = "🍎", nome = "Maçã", tema = Temas.FRUTAS),
        Carta(simbolo = "🍌", nome = "Banana", tema = Temas.FRUTAS),
        Carta(simbolo = "🍇", nome = "Uva", tema = Temas.FRUTAS),
        Carta(simbolo = "🍓", nome = "Morango", tema = Temas.FRUTAS),
        Carta(simbolo = "🍉", nome = "Melancia", tema = Temas.FRUTAS),
        Carta(simbolo = "🍍", nome = "Abacaxi", tema = Temas.FRUTAS),
        Carta(simbolo = "🍊", nome = "Laranja", tema = Temas.FRUTAS),
        Carta(simbolo = "🥝", nome = "Kiwi", tema = Temas.FRUTAS),

        Carta(simbolo = "⚽", nome = "Futebol", tema = Temas.ESPORTES),
        Carta(simbolo = "🏀", nome = "Basquete", tema = Temas.ESPORTES),
        Carta(simbolo = "🏐", nome = "Vôlei", tema = Temas.ESPORTES),
        Carta(simbolo = "🎾", nome = "Tênis", tema = Temas.ESPORTES),
        Carta(simbolo = "🏈", nome = "Futebol Americano", tema = Temas.ESPORTES),
        Carta(simbolo = "⚾", nome = "Beisebol", tema = Temas.ESPORTES),
        Carta(simbolo = "🏓", nome = "Pingue-pongue", tema = Temas.ESPORTES),
        Carta(simbolo = "🥊", nome = "Boxe", tema = Temas.ESPORTES)
    )
}
