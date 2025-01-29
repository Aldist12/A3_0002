package com.example.mystokapp.ui.view.pemasok

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
                title = DestinasiPemasokEntry.titleRes,
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
                onValueChange = viewModel::updateInsertPemasokState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertPemasok()
                        snackbarHostState.showSnackbar("Pemasok berhasil disimpan")
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
            value = insertUiEvent.namaPemasok,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPemasok = it)) },
            label = { Text("Nama Pemasok", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar mudah dibaca**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Pemasok", color = Color.Gray) },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.alamatPemasok,
            onValueChange = { onValueChange(insertUiEvent.copy(alamatPemasok = it)) },
            label = { Text("Alamat Pemasok", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Alamat Pemasok", color = Color.Gray) },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.teleponPemasok,
            onValueChange = { onValueChange(insertUiEvent.copy(teleponPemasok = it)) },
            label = { Text("Telepon Pemasok", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Telepon Pemasok", color = Color.Gray) },
            singleLine = true
        )
    }
}
