package com.linh.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.linh.journal.data.dao.JournalDao
import com.linh.journal.data.entity.JournalEntry
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DialogState(
    val isVisible: Boolean = false,
    val title: String = "",
    val content: String = "",
)

class JournalViewModel(private val dao: JournalDao) : ViewModel() {

    val entries: StateFlow<List<JournalEntry>> = dao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState: StateFlow<DialogState> = _dialogState.asStateFlow()

    fun openAddDialog() {
        _dialogState.value = DialogState(isVisible = true)
    }

    fun dismissDialog() {
        _dialogState.value = DialogState()
    }

    fun onTitleChange(title: String) {
        _dialogState.value = _dialogState.value.copy(title = title)
    }

    fun onContentChange(content: String) {
        _dialogState.value = _dialogState.value.copy(content = content)
    }

    fun saveEntry() {
        val state = _dialogState.value
        if (state.title.isBlank()) return
        val now = currentTimeMillis()
        viewModelScope.launch {
            dao.insert(
                JournalEntry(
                    title = state.title.trim(),
                    content = state.content.trim(),
                    createdAt = now,
                    updatedAt = now,
                )
            )
        }
        dismissDialog()
    }

    companion object {
        fun factory(dao: JournalDao): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                    return JournalViewModel(dao) as T
                }
            }
    }
}
