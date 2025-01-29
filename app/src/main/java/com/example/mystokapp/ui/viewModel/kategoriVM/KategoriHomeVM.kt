package com.example.mystokapp.ui.viewModel.kategoriVM


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mystokapp.model.Kategori
import com.example.mystokapp.repository.KategoriRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

sealed class HomeUiState {
    data class Success @OptIn(InternalSerializationApi::class) constructor(val kategori: List<Kategori>) :
        HomeUiState()

    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class KategoriHomeVM(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var kategoriUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getKategori()
    }

    @OptIn(InternalSerializationApi::class)
    fun getKategori() {
        viewModelScope.launch {
            kategoriUiState = HomeUiState.Loading
            kategoriUiState = try {
                HomeUiState.Success(kategoriRepository.getKategori())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteKategori(id: Int) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(id)
            } catch (e: IOException) {
                kategoriUiState = HomeUiState.Error
            } catch (e: HttpException) {
                kategoriUiState = HomeUiState.Error
            }
        }
    }
}