package com.example.mystokapp.ui.viewModel.produkVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Produk
import com.example.mystokapp.repository.ProdukRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

sealed class DetailProdukUiState {
    object Loading : DetailProdukUiState()
    data class Success @OptIn(InternalSerializationApi::class) constructor(val produk: Produk) : DetailProdukUiState()
    object Error : DetailProdukUiState()
}

class ProdukDetailVM(private val repository: ProdukRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailProdukUiState>(DetailProdukUiState.Loading)
    val uiState: StateFlow<DetailProdukUiState> = _uiState.asStateFlow()

    // Mendapatkan produk berdasarkan id
    @OptIn(InternalSerializationApi::class)
    fun getProdukById(idProduk: Int) {
        viewModelScope.launch {
            _uiState.value = DetailProdukUiState.Loading
            try {
                val produk = repository.getProdukById(idProduk)
                _uiState.value = DetailProdukUiState.Success(produk)
            } catch (e: Exception) {
                _uiState.value = DetailProdukUiState.Error
            }
        }
    }

    // Memperbarui produk
    @OptIn(InternalSerializationApi::class)
    fun updateProduk(idProduk: Int, produk: Produk) {
        viewModelScope.launch {
            _uiState.value = DetailProdukUiState.Loading
            try {
                repository.updateProduk(idProduk, produk)
                _uiState.value = DetailProdukUiState.Success(produk)
            } catch (e: Exception) {
                _uiState.value = DetailProdukUiState.Error
            }
        }
    }
}
