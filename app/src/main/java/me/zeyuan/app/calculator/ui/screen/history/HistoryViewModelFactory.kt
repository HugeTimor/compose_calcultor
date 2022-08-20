package me.zeyuan.app.calculator.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.zeyuan.app.calculator.store.HistoryRepository

class HistoryViewModelFactory(private val repository: HistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HistoryViewModel(repository) as T
}