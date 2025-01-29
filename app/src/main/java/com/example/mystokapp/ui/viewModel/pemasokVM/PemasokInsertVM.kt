package com.example.mystokapp.ui.viewModel.pemasokVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Pemasok
import com.example.mystokapp.repository.PemasokRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

class PemasokInsertVM(private val pemasokRepository: PemasokRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPemasokState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    @OptIn(InternalSerializationApi::class)
    suspend fun insertPemasok() {
        viewModelScope.launch {
            try {
                pemasokRepository.insertPemasok(uiState.insertUiEvent.toPemasok())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idPemasok: Int = 0,
    val namaPemasok: String = "",
    val alamatPemasok: String = "",
    val teleponPemasok: String = ""
)

@OptIn(InternalSerializationApi::class)
fun InsertUiEvent.toPemasok(): Pemasok = Pemasok(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)

@OptIn(InternalSerializationApi::class)
fun Pemasok.toUiStatePemasok(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

@OptIn(InternalSerializationApi::class)
fun Pemasok.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)
