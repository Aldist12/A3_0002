package com.example.mystokapp.ui.view.pemasok

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokUpdateVM
import com.example.mystokapp.ui.viewModel.pemasokVM.UpdatePemasokEvent
import com.example.mystokapp.ui.viewModel.pemasokVM.UpdatePemasokUiState
import kotlinx.serialization.InternalSerializationApi

object DestinasiUpdatePemasok : DestinasiNavigasi {
    override val route = "update pemasok"
    override val titleRes = "Update Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun UpdateScreenPemasok(
    idPemasok: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PemasokUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(idPemasok) {
        viewModel.loadPemasokData(idPemasok)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePemasok.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray) // **Menggunakan warna abu-abu terang agar konsisten**
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                uiState is UpdatePemasokUiState.Loading -> CircularProgressIndicator()
                uiState is UpdatePemasokUiState.Success -> {
                    val pemasok = (uiState as UpdatePemasokUiState.Success).pemasok
                    UpdateFormPemasok(
                        idPemasok = pemasok.idPemasok,
                        namaPemasok = pemasok.namaPemasok,
                        alamatPemasok = pemasok.alamatPemasok,
                        teleponPemasok = pemasok.teleponPemasok,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updatePemasok()
                            onNavigateBack()
                        }
                    )
                }
                uiState is UpdatePemasokUiState.Error -> {
                    Text(
                        text = "Error: ${(uiState as UpdatePemasokUiState.Error).message}",
                        color = Color.Red, // **Menampilkan error dalam warna merah**
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFormPemasok(
    idPemasok: Int,
    namaPemasok: String,
    alamatPemasok: String,
    teleponPemasok: String,
    onUpdateClick: (UpdatePemasokEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var namaPemasokState by remember { mutableStateOf(namaPemasok) }
    var alamatPemasokState by remember { mutableStateOf(alamatPemasok) }
    var teleponPemasokState by remember { mutableStateOf(teleponPemasok) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaPemasokState,
            onValueChange = { namaPemasokState = it },
            label = { Text("Nama Pemasok", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar mudah dibaca**
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Masukkan Nama Pemasok", color = Color.Gray) }
        )

        OutlinedTextField(
            value = alamatPemasokState,
            onValueChange = { alamatPemasokState = it },
            label = { Text("Alamat Pemasok", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            placeholder = { Text("Masukkan Alamat Pemasok", color = Color.Gray) }
        )

        OutlinedTextField(
            value = teleponPemasokState,
            onValueChange = { teleponPemasokState = it },
            label = { Text("Telepon Pemasok", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            placeholder = { Text("Masukkan Nomor Telepon", color = Color.Gray) }
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdatePemasokEvent(
                        idPemasok = idPemasok,
                        namaPemasok = namaPemasokState,
                        alamatPemasok = alamatPemasokState,
                        teleponPemasok = teleponPemasokState
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C5CE7), // **Tombol tetap ungu**
                contentColor = Color.White
            )
        ) {
            Text("Update", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
        }
    }
}
