package com.example.mystokapp.ui.view.kategori

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriUpdateVM
import com.example.mystokapp.ui.viewModel.kategoriVM.UpdateKategoriEvent
import com.example.mystokapp.ui.viewModel.kategoriVM.UpdateKategoriUiState
import kotlinx.serialization.InternalSerializationApi


object DestinasiUpdateKategori : DestinasiNavigasi {
    override val route = "update kategori"
    override val titleRes = "Update Kategori"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun UpdateScreenKategori(
    idKategori: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(idKategori) {
        viewModel.loadKategoriData(idKategori)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateKategori.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is UpdateKategoriUiState.Loading -> CircularProgressIndicator()
                is UpdateKategoriUiState.Success -> {
                    val kategori = (uiState as UpdateKategoriUiState.Success).kategori
                    UpdateFormKategori(
                        idKategori = kategori.idKategori,
                        namaKategori = kategori.namaKategori,
                        deskripsiKategori = kategori.deskripsiKategori,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateKategori()
                            onNavigateBack()
                        }
                    )
                }

                is UpdateKategoriUiState.Error -> {
                    Text("Error: ${(uiState as UpdateKategoriUiState.Error).message}")
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFormKategori(
    idKategori: Int,
    namaKategori: String,
    deskripsiKategori: String,
    onUpdateClick: (UpdateKategoriEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var namaKategoriState by remember { mutableStateOf(namaKategori) }
    var deskripsiKategoriState by remember { mutableStateOf(deskripsiKategori) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaKategoriState,
            onValueChange = { namaKategoriState = it },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsiKategoriState,
            onValueChange = { deskripsiKategoriState = it },
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateKategoriEvent(
                        idKategori = idKategori,
                        namaKategori = namaKategoriState,
                        deskripsiKategori = deskripsiKategoriState
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
