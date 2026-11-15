package com.example.jogodamemoria.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities = [Carta::class, Partida::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartaDAO(): CartaDAO
    abstract fun partidaDAO(): PartidaDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "jogo_memoria_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
