package com.example.mystokapp.ui.viewModel.produkVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Produk
import com.example.mystokapp.repository.ProdukRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

class ProdukInsertVM(private val produkRepository: ProdukRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertProdukState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    @OptIn(InternalSerializationApi::class)
    suspend fun insertProduk() {
        viewModelScope.launch {
            try {
                produkRepository.insertProduk(uiState.insertUiEvent.toProduk())
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
    val idProduk: Int = 0,
    val namaProduk: String = "",
    val deskripsiProduk: String = "",
    val harga: String = "",
    val stok: String = "",
    val idKategori: String = "",
    val idPemasok: String = "",
    val idMerk: String = ""
)

@OptIn(InternalSerializationApi::class)
fun InsertUiEvent.toProduk(): Produk = Produk(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)

@OptIn(InternalSerializationApi::class)
fun Produk.toUiStateProduk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

@OptIn(InternalSerializationApi::class)
fun Produk.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)
