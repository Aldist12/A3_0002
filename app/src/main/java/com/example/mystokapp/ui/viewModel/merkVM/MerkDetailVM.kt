package com.example.mystokapp.ui.viewModel.merkVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Merk
import com.example.mystokapp.repository.MerkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

sealed class DetailMerkUiState {
    object Loading : DetailMerkUiState()
    data class Success @OptIn(InternalSerializationApi::class) constructor(val merk: Merk) :
        DetailMerkUiState()

    object Error : DetailMerkUiState()
}

class MerkDetailVM(private val repository: MerkRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailMerkUiState>(DetailMerkUiState.Loading)
    val uiState: StateFlow<DetailMerkUiState> = _uiState.asStateFlow()

    // Mendapatkan detail merk berdasarkan id
    @OptIn(InternalSerializationApi::class)
    fun getMerkById(idMerk: Int) {
        viewModelScope.launch {
            _uiState.value = DetailMerkUiState.Loading
            try {
                val merk = repository.getMerkById(idMerk)
                _uiState.value = DetailMerkUiState.Success(merk)
            } catch (e: Exception) {
                _uiState.value = DetailMerkUiState.Error
            }
        }
    }

    // Memperbarui merk
    @OptIn(InternalSerializationApi::class)
    fun updateMerk(idMerk: Int, merk: Merk) {
        viewModelScope.launch {
            _uiState.value = DetailMerkUiState.Loading
            try {
                repository.updateMerk(idMerk, merk)
                _uiState.value = DetailMerkUiState.Success(merk)
            } catch (e: Exception) {
                _uiState.value = DetailMerkUiState.Error
            }
        }
    }
}
