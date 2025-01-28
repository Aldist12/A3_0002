package com.example.mystokapp.ui.view.kategori


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.kategoriVM.InsertUiEvent
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriInsertVM
import kotlinx.coroutines.launch

object DestinasiKategoriEntry : DestinasiNavigasi {
    override val route = "kategori entry"
    override val titleRes = "Masukkan Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKategoriScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CostumeTopAppBar(
                title = DestinasiKategoriEntry.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormInput(
                insertUiEvent = uiState.insertUiEvent,
                onValueChange = viewModel::updateInsertKategoriState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertKategori()
                        snackbarHostState.showSnackbar("Kategori berhasil disimpan")
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = insertUiEvent.namaKategori,
            onValueChange = { onValueChange(insertUiEvent.copy(namaKategori = it)) },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Kategori") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiKategori,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiKategori = it)) },
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Deskripsi Kategori") },
            singleLine = true
        )
    }
}
