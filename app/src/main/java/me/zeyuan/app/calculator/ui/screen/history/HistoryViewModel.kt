package me.zeyuan.app.calculator.ui.screen.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.zeyuan.app.calculator.store.HistoryEntity
import me.zeyuan.app.calculator.store.HistoryRepository

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    var histories by mutableStateOf(emptyList<HistoryEntity>())

    fun getAllHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll().collect {
                if (it.isEmpty()) return@collect
                histories = it
            }
        }
    }

    fun storeHistory(expression: String, result: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val entity = HistoryEntity(expression, result)
                repository.insert(entity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}