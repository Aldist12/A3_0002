package com.example.mystokapp.ui.viewModel.pemasokVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mystokapp.model.Pemasok
import com.example.mystokapp.repository.PemasokRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

sealed class HomeUiState {
    data class Success @OptIn(InternalSerializationApi::class) constructor(val pemasok: List<Pemasok>) :
        HomeUiState()

    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class PemasokHomeVM(private val pemasokRepository: PemasokRepository) : ViewModel() {
    var pemasokUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPemasok()
    }

    @OptIn(InternalSerializationApi::class)
    fun getPemasok() {
        viewModelScope.launch {
            pemasokUiState = HomeUiState.Loading
            pemasokUiState = try {
                HomeUiState.Success(pemasokRepository.getPemasok())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deletePemasok(id: Int) {
        viewModelScope.launch {
            try {
                pemasokRepository.deletePemasok(id)
            } catch (e: IOException) {
                pemasokUiState = HomeUiState.Error
            } catch (e: HttpException) {
                pemasokUiState = HomeUiState.Error
            }
        }
    }
}
