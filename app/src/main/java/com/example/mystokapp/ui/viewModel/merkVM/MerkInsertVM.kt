package com.example.mystokapp.ui.viewModel.merkVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Merk
import com.example.mystokapp.repository.MerkRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

class MerkInsertVM(private val merkRepository: MerkRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMerkState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    @OptIn(InternalSerializationApi::class)
    fun insertMerk() {
        viewModelScope.launch {
            try {
                val merk = uiState.insertUiEvent.toMerk()
                merkRepository.insertMerk(merk)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the error properly, e.g., updating a UI state or logging
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idMerk: Int = 0,
    val namaMerk: String = "",
    val deskripsiMerk: String = ""
)

@OptIn(InternalSerializationApi::class)
fun InsertUiEvent.toMerk(): Merk = Merk(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)

@OptIn(InternalSerializationApi::class)
fun Merk.toUiStateMerk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

@OptIn(InternalSerializationApi::class)
fun Merk.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)
