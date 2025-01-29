package com.example.mystokapp.ui.viewModel.kategoriVM

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Kategori
import com.example.mystokapp.repository.KategoriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

sealed class DetailKategoriUiState {
    object Loading : DetailKategoriUiState()
    data class Success @OptIn(InternalSerializationApi::class) constructor(val kategori: Kategori) :
        DetailKategoriUiState()

    object Error : DetailKategoriUiState()
}

class KategoriDetailVM(private val repository: KategoriRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailKategoriUiState>(DetailKategoriUiState.Loading)
    val uiState: StateFlow<DetailKategoriUiState> = _uiState.asStateFlow()

    // Mendapatkan detail kategori berdasarkan id
    @OptIn(InternalSerializationApi::class)
    fun getKategoriById(idKategori: Int) {
        viewModelScope.launch {
            _uiState.value = DetailKategoriUiState.Loading
            try {
                val kategori = repository.getKategoriById(idKategori)
                _uiState.value = DetailKategoriUiState.Success(kategori)
            } catch (e: Exception) {
                _uiState.value = DetailKategoriUiState.Error
            }
        }
    }

    // Memperbarui kategori
    @OptIn(InternalSerializationApi::class)
    fun updateKategori(idKategori: Int, kategori: Kategori) {
        viewModelScope.launch {
            _uiState.value = DetailKategoriUiState.Loading
            try {
                repository.updateKategori(idKategori, kategori)
                _uiState.value = DetailKategoriUiState.Success(kategori)
            } catch (e: Exception) {
                _uiState.value = DetailKategoriUiState.Error
            }
        }
    }
}
