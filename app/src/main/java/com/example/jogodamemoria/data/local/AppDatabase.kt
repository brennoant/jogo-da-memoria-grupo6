package com.example.jogodamemoria.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

// O banco local do app. Lista as tabelas (entities) e a versão do banco.
@Database(entities = [Carta::class, Partida::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // O Room gera a implementação destas funções; cada uma devolve o DAO da sua tabela
    abstract fun cartaDAO(): CartaDAO
    abstract fun partidaDAO(): PartidaDAO

    companion object {
        // @Volatile: toda thread enxerga o valor mais novo de INSTANCE, sem cópia velha em cache
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton: o app inteiro usa um único objeto do banco
        fun getDatabase(context: Context): AppDatabase {
            // Se já existe, devolve. Se não, entra no synchronized para criar.
            return INSTANCE ?: synchronized(this) {
                // synchronized: só uma thread por vez entra aqui, então nunca são criados dois bancos
                val instance = Room.databaseBuilder(
                    context.applicationContext, // contexto do app (não da tela) para não vazar memória
                    AppDatabase::class.java,
                    "jogo_memoria_db"           // nome do arquivo do banco no celular
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
