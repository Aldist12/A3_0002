package com.example.mystokapp.ui.view.pemasok


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
import com.example.mystokapp.ui.view.produk.DestinasiEntry
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.pemasokVM.InsertUiEvent
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokInsertVM

import kotlinx.coroutines.launch

object DestinasiPemasokEntry : DestinasiNavigasi {
    override val route = "pemasok entry"
    override val titleRes = "Masukkan Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPemasokScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PemasokInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
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
                onValueChange = viewModel::updateInsertPemasokState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertPemasok()
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
            value = insertUiEvent.namaPemasok,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPemasok = it)) },
            label = { Text("Nama Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Pemasok") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.alamatPemasok,
            onValueChange = { onValueChange(insertUiEvent.copy(alamatPemasok = it)) },
            label = { Text("Alamat Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Alamat Pemasok") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.teleponPemasok,
            onValueChange = { onValueChange(insertUiEvent.copy(teleponPemasok = it)) },
            label = { Text("Telepon Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Telepon Pemasok") },
            singleLine = true
        )
    }
}
