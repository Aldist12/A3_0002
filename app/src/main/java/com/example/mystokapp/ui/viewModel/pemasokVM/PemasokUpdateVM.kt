package com.example.mystokapp.ui.viewModel.pemasokVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystokapp.model.Pemasok
import com.example.mystokapp.repository.PemasokRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

data class UpdatePemasokEvent(
    val idPemasok: Int = 0,
    val namaPemasok: String,
    val alamatPemasok: String,
    val teleponPemasok: String
)

sealed class UpdatePemasokUiState {
    object Idle : UpdatePemasokUiState()
    object Loading : UpdatePemasokUiState()
    data class Success @OptIn(InternalSerializationApi::class) constructor(val pemasok: Pemasok) : UpdatePemasokUiState()
    data class Error(val message: String) : UpdatePemasokUiState()
}

class PemasokUpdateVM(
    private val pemasokRepository: PemasokRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdatePemasokUiState>(UpdatePemasokUiState.Idle)
    val uiState: StateFlow<UpdatePemasokUiState> = _uiState

    private var currentFormData: UpdatePemasokEvent? = null

    @OptIn(InternalSerializationApi::class)
    fun loadPemasokData(idPemasok: Int) {
        _uiState.value = UpdatePemasokUiState.Loading
        viewModelScope.launch {
            try {
                val pemasok = pemasokRepository.getPemasokById(idPemasok)
                currentFormData = UpdatePemasokEvent(
                    idPemasok = pemasok.idPemasok,
                    namaPemasok = pemasok.namaPemasok,
                    alamatPemasok = pemasok.alamatPemasok,
                    teleponPemasok = pemasok.teleponPemasok
                )
                _uiState.value = UpdatePemasokUiState.Success(pemasok)
            } catch (e: Exception) {
                _uiState.value = UpdatePemasokUiState.Error("Failed to load supplier data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdatePemasokEvent) {
        currentFormData = event
    }

    @OptIn(InternalSerializationApi::class)
    fun updatePemasok() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdatePemasokUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdatePemasokUiState.Loading
        viewModelScope.launch {
            try {
                val pemasok = Pemasok(
                    idPemasok = formData.idPemasok,
                    namaPemasok = formData.namaPemasok,
                    alamatPemasok = formData.alamatPemasok,
                    teleponPemasok = formData.teleponPemasok
                )
                pemasokRepository.updatePemasok(formData.idPemasok, pemasok)
                _uiState.value = UpdatePemasokUiState.Success(pemasok)
            } catch (e: Exception) {
                _uiState.value = UpdatePemasokUiState.Error("Failed to update supplier: ${e.message}")
            }
        }
    }
}
