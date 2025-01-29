package com.example.mystokapp.ui.viewModel.merkVM
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mystokapp.model.Merk
import com.example.mystokapp.repository.MerkRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

sealed class HomeUiState {
    data class Success @OptIn(InternalSerializationApi::class) constructor(val merk: List<Merk>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class MerkHomeVM(private val merkRepository: MerkRepository) : ViewModel() {
    var merkUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMerk()
    }

    @OptIn(InternalSerializationApi::class)
    fun getMerk() {
        viewModelScope.launch {
            merkUiState = HomeUiState.Loading
            merkUiState = try {
                HomeUiState.Success(merkRepository.getMerk())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteMerk(id: Int) {
        viewModelScope.launch {
            try {
                merkRepository.deleteMerk(id)
            } catch (e: IOException) {
                merkUiState = HomeUiState.Error
            } catch (e: HttpException) {
                merkUiState = HomeUiState.Error
            }
        }
    }
}
