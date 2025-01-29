package com.example.mystokapp.ui.view.produk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.model.MenuOptions
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.customWidget.DynamicSelectedTextField
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.produkVM.InsertUiEvent
import com.example.mystokapp.ui.viewModel.produkVM.ProdukInsertVM
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "produk entry"
    override val titleRes = "Masukkan Produk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryProdukScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProdukInsertVM = viewModel(factory = PenyediaViewModel.Factory)
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
                .background(Color.LightGray) // **Menggunakan warna abu-abu terang agar konsisten**
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormInput(
                insertUiEvent = uiState.insertUiEvent,
                onValueChange = viewModel::updateInsertProdukState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertProduk()
                        snackbarHostState.showSnackbar("Produk berhasil disimpan!")
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C5CE7), // Warna ungu untuk tombol
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
            value = insertUiEvent.namaProduk,
            onValueChange = { onValueChange(insertUiEvent.copy(namaProduk = it)) },
            label = { Text("Nama Produk", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar mudah dibaca**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Produk", color = Color.Gray) },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiProduk,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiProduk = it)) },
            label = { Text("Deskripsi Produk", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Deskripsi Produk", color = Color.Gray) },
            singleLine = false
        )

        OutlinedTextField(
            value = insertUiEvent.harga,
            onValueChange = { onValueChange(insertUiEvent.copy(harga = it)) },
            label = { Text("Harga", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Harga", color = Color.Gray) },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.stok,
            onValueChange = { onValueChange(insertUiEvent.copy(stok = it)) },
            label = { Text("Stok", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Stok", color = Color.Gray) },
            singleLine = true
        )

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idKategori,
            options = MenuOptions.kategoriOption(),
            label = "Nama Kategori",
            onValueChangedEvent = { selectedKategori ->
                onValueChange(insertUiEvent.copy(idKategori = selectedKategori))
            }
        )

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idMerk,
            options = MenuOptions.merkOption(),
            label = "Nama Merk",
            onValueChangedEvent = { selectedMerk ->
                onValueChange(insertUiEvent.copy(idMerk = selectedMerk))
            }
        )

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idPemasok,
            options = MenuOptions.pemasokOption(),
            label = "Nama Pemasok",
            onValueChangedEvent = { selectedPemasok ->
                onValueChange(insertUiEvent.copy(idPemasok = selectedPemasok))
            }
        )
    }
}
