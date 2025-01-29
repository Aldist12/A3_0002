package com.example.mystokapp.ui.view.merk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.merkVM.InsertUiEvent
import com.example.mystokapp.ui.viewModel.merkVM.MerkInsertVM
import kotlinx.coroutines.launch

object DestinasiMerkEntry : DestinasiNavigasi {
    override val route = "merk entry"
    override val titleRes = "Masukkan Merk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMerkScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MerkInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CostumeTopAppBar(
                title = DestinasiMerkEntry.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray) // **Menggunakan warna abu-abu terang agar konsisten**
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormInput(
                insertUiEvent = uiState.insertUiEvent,
                onValueChange = viewModel::updateInsertMerkState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertMerk()
                        snackbarHostState.showSnackbar("Merk berhasil disimpan")
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C5CE7), // **Tombol tetap ungu**
                    contentColor = Color.White
                )
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
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
            value = insertUiEvent.namaMerk,
            onValueChange = { onValueChange(insertUiEvent.copy(namaMerk = it)) },
            label = { Text("Nama Merk", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar mudah dibaca**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Merk", color = Color.Gray) },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiMerk,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiMerk = it)) },
            label = { Text("Deskripsi Merk", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Deskripsi Merk", color = Color.Gray) },
            singleLine = true
        )
    }
}
