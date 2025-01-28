package com.example.mystokapp.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriHomeVM
import com.example.mystokapp.ui.viewModel.merkVM.MerkHomeVM
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokHomeVM
import kotlinx.serialization.InternalSerializationApi
import kotlin.collections.map

object MenuOptions {

    @OptIn(InternalSerializationApi::class)
    @Composable
    fun merkOption(
        viewModel: MerkHomeVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val merkState = viewModel.merkUiState
        return when (merkState) {
            is com.example.mystokapp.ui.viewModel.merkVM.HomeUiState.Success -> merkState.merk.map { it.namaMerk }
            else -> emptyList()
        }
    }

    @OptIn(InternalSerializationApi::class)
    @Composable
    fun pemasokOption(
        viewModel: PemasokHomeVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val pemasokState = viewModel.pemasokUiState
        return when (pemasokState) {
            is com.example.mystokapp.ui.viewModel.pemasokVM.HomeUiState.Success -> pemasokState.pemasok.map { it.namaPemasok }
            else -> emptyList()
        }
    }

    @OptIn(InternalSerializationApi::class)
    @Composable
    fun kategoriOption(
        viewModel: KategoriHomeVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val kategoriState = viewModel.kategoriUiState
        return when (kategoriState) {
            is com.example.mystokapp.ui.viewModel.kategoriVM.HomeUiState.Success -> kategoriState.kategori.map { it.namaKategori }
            else -> emptyList()
        }
    }
}