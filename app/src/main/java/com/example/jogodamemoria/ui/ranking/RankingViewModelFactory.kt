package com.example.jogodamemoria.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jogodamemoria.data.repository.PartidasRepository

// Padrão obrigatório do professor: Factory manual (sem Hilt/Koin)
// O AppNavigation/Tela usa esta fábrica para injetar o Repository no ViewModel
class RankingViewModelFactory(
    private val repository: PartidasRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankingViewModel::class.java)) {
            return RankingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
