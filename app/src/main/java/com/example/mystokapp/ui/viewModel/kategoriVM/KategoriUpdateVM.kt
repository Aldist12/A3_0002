package com.example.mystokapp.ui.viewModel.kategoriVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Kategori
import com.example.mystokapp.repository.KategoriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

data class UpdateKategoriEvent(
    val idKategori: Int = 0,
    val namaKategori: String,
    val deskripsiKategori: String
)

sealed class UpdateKategoriUiState {
    object Idle : UpdateKategoriUiState()
    object Loading : UpdateKategoriUiState()
    data class Success @OptIn(InternalSerializationApi::class) constructor(val kategori: Kategori) : UpdateKategoriUiState()
    data class Error(val message: String) : UpdateKategoriUiState()
}

class KategoriUpdateVM(
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateKategoriUiState>(UpdateKategoriUiState.Idle)
    val uiState: StateFlow<UpdateKategoriUiState> = _uiState

    private var currentFormData: UpdateKategoriEvent? = null

    @OptIn(InternalSerializationApi::class)
    fun loadKategoriData(idKategori: Int) {
        _uiState.value = UpdateKategoriUiState.Loading
        viewModelScope.launch {
            try {
                val kategori = kategoriRepository.getKategoriById(idKategori)
                currentFormData = UpdateKategoriEvent(
                    idKategori = kategori.idKategori,
                    namaKategori = kategori.namaKategori,
                    deskripsiKategori = kategori.deskripsiKategori
                )
                _uiState.value = UpdateKategoriUiState.Success(kategori)
            } catch (e: Exception) {
                _uiState.value = UpdateKategoriUiState.Error("Failed to load category data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateKategoriEvent) {
        currentFormData = event
    }

    @OptIn(InternalSerializationApi::class)
    fun updateKategori() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateKategoriUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateKategoriUiState.Loading
        viewModelScope.launch {
            try {
                val kategori = Kategori(
                    idKategori = formData.idKategori,
                    namaKategori = formData.namaKategori,
                    deskripsiKategori = formData.deskripsiKategori
                )
                kategoriRepository.updateKategori(formData.idKategori, kategori)
                _uiState.value = UpdateKategoriUiState.Success(kategori)
            } catch (e: Exception) {
                _uiState.value = UpdateKategoriUiState.Error("Failed to update category: ${e.message}")
            }
        }
    }
}
