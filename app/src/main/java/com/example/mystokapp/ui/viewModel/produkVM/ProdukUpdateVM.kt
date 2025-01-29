package com.example.mystokapp.ui.viewModel.produkVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Produk
import com.example.mystokapp.repository.ProdukRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

data class UpdateUiEvent(
    val idProduk: Int = 0,
    val namaProduk: String,
    val deskripsiProduk: String,
    val harga: String,
    val stok: String,
    val idKategori: String,
    val idPemasok: String,
    val idMerk: String
)

sealed class UpdateUiState {
    object Idle : UpdateUiState()
    object Loading : UpdateUiState()
    data class Success @OptIn(InternalSerializationApi::class) constructor(val produk: Produk) : UpdateUiState()
    data class Error(val message: String) : UpdateUiState()
}

class UpdateProdukVM(
    private val produkRepository: ProdukRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.Idle)
    val uiState: StateFlow<UpdateUiState> = _uiState

    private var currentFormData: UpdateUiEvent? = null

    @OptIn(InternalSerializationApi::class)
    fun loadProdukData(idProduk: Int) {
        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val produk = produkRepository.getProdukById(idProduk)
                currentFormData = UpdateUiEvent(
                    idProduk = produk.idProduk,
                    namaProduk = produk.namaProduk,
                    deskripsiProduk = produk.deskripsiProduk,
                    harga = produk.harga,
                    stok = produk.stok,
                    idKategori = produk.idKategori,
                    idPemasok = produk.idPemasok,
                    idMerk = produk.idMerk
                )
                _uiState.value = UpdateUiState.Success(produk)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to load product data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateUiEvent) {
        currentFormData = event
    }

    @OptIn(InternalSerializationApi::class)
    fun updateProduk() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateUiState.Loading
        viewModelScope.launch {
            try {
                val produk = Produk(
                    idProduk = formData.idProduk,
                    namaProduk = formData.namaProduk,
                    deskripsiProduk = formData.deskripsiProduk,
                    harga = formData.harga,
                    stok = formData.stok,
                    idKategori = formData.idKategori,
                    idPemasok = formData.idPemasok,
                    idMerk = formData.idMerk
                )
                produkRepository.updateProduk(formData.idProduk, produk)
                _uiState.value = UpdateUiState.Success(produk)
            } catch (e: Exception) {
                _uiState.value = UpdateUiState.Error("Failed to update product: ${e.message}")
            }
        }
    }
}