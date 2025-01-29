package com.example.mystokapp.ui.viewModel.produkVM

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mystokapp.model.Produk
import com.example.mystokapp.repository.ProdukRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

sealed class HomeUiState {
    data class Success @OptIn(InternalSerializationApi::class) constructor(val produk: List<Produk>) :
        HomeUiState()

    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class ProdukHomeVM(private val produkRepository: ProdukRepository) : ViewModel() {
    var produkUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getProduk()
    }

    @OptIn(InternalSerializationApi::class)
    fun getProduk() {
        viewModelScope.launch {
            produkUiState = HomeUiState.Loading
            produkUiState = try {
                HomeUiState.Success(produkRepository.getProduk())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteProduk(id: Int) {
        viewModelScope.launch {
            try {
                produkRepository.deleteProduk(id)
            } catch (e: IOException) {
                produkUiState = HomeUiState.Error
            } catch (e: HttpException) {
                produkUiState = HomeUiState.Error
            }
        }
    }
}
