package com.example.mystokapp.ui.view.merk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.merkVM.MerkUpdateVM
import com.example.mystokapp.ui.viewModel.merkVM.UpdateMerkEvent
import com.example.mystokapp.ui.viewModel.merkVM.UpdateMerkUiState
import kotlinx.serialization.InternalSerializationApi

object DestinasiUpdateMerk : DestinasiNavigasi {
    override val route = "update merk"
    override val titleRes = "Update Merk"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun UpdateScreenMerk(
    idMerk: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MerkUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(idMerk) {
        viewModel.loadMerkData(idMerk)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateMerk.titleRes,
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
                uiState is UpdateMerkUiState.Loading -> CircularProgressIndicator()
                uiState is UpdateMerkUiState.Success -> {
                    val merk = (uiState as UpdateMerkUiState.Success).merk
                    UpdateFormMerk(
                        idMerk = merk.idMerk,
                        namaMerk = merk.namaMerk,
                        deskripsiMerk = merk.deskripsiMerk,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateMerk()
                            onNavigateBack()
                        }
                    )
                }
                uiState is UpdateMerkUiState.Error -> {
                    Text(
                        text = "Error: ${(uiState as UpdateMerkUiState.Error).message}",
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
fun UpdateFormMerk(
    idMerk: Int,
    namaMerk: String,
    deskripsiMerk: String,
    onUpdateClick: (UpdateMerkEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var namaMerkState by remember { mutableStateOf(namaMerk) }
    var deskripsiMerkState by remember { mutableStateOf(deskripsiMerk) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaMerkState,
            onValueChange = { namaMerkState = it },
            label = { Text("Nama Merk", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar mudah dibaca**
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Masukkan Nama Merk", color = Color.Gray) }
        )

        OutlinedTextField(
            value = deskripsiMerkState,
            onValueChange = { deskripsiMerkState = it },
            label = { Text("Deskripsi Merk", color = Color(0xFF2C2C2C)) }, // **Teks lebih gelap agar lebih jelas**
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            placeholder = { Text("Masukkan Deskripsi Merk", color = Color.Gray) }
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateMerkEvent(
                        idMerk = idMerk,
                        namaMerk = namaMerkState,
                        deskripsiMerk = deskripsiMerkState
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
