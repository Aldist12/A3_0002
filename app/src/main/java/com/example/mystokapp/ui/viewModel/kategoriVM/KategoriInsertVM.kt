package com.example.mystokapp.ui.viewModel.kategoriVM

import com.example.mystokapp.repository.KategoriRepository
import kotlinx.serialization.InternalSerializationApi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Kategori
import kotlinx.coroutines.launch

class KategoriInsertVM(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertKategoriState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    @OptIn(InternalSerializationApi::class)
    fun insertKategori() {
        viewModelScope.launch {
            try {
                val kategori = uiState.insertUiEvent.toKategori()
                kategoriRepository.insertKategori(kategori)
            } catch (e: Exception) {
                e.printStackTrace()
                // Tangani error, misalnya dengan memperbarui UI state atau logging
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idKategori: Int = 0,
    val namaKategori: String = "",
    val deskripsiKategori: String = ""
)

@OptIn(InternalSerializationApi::class)
fun InsertUiEvent.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)

@OptIn(InternalSerializationApi::class)
fun Kategori.toUiStateKategori(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

@OptIn(InternalSerializationApi::class)
fun Kategori.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)
