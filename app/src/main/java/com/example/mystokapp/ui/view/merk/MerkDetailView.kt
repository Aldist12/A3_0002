package com.example.mystokapp.ui.view.merk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.model.Merk
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.merkVM.DetailMerkUiState
import com.example.mystokapp.ui.viewModel.merkVM.MerkDetailVM
import kotlinx.serialization.InternalSerializationApi

object DestinasiMerkDetail : DestinasiNavigasi {
    override val route = "detail merk"
    override val titleRes = "Detail Merk"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun DetailMerkScreen(
    idMerk: Int,
    onNavigateBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: MerkDetailVM = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(idMerk) {
        viewModel.getMerkById(idMerk)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiMerkDetail.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idMerk) },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF6C5CE7), // **Tombol tetap ungu**
                contentColor = Color.White,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Merk",
                    tint = Color.White
                )
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailMerkUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailMerkUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color.LightGray) // **Menggunakan warna abu-abu terang agar konsisten**
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailMerkCard(merk = state.merk)
                }
            }

            is DetailMerkUiState.Error -> {
                Text(
                    text = "Error: ${(state as DetailMerkUiState.Error)}",
                    color = Color.Red, // **Menampilkan error dalam warna merah**
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun DetailMerkCard(
    merk: Merk, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD0C4FC) // **Warna card tetap ungu muda**
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header section with merk name
            Text(
                text = merk.namaMerk,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF2C2C2C), // **Warna lebih gelap agar mudah dibaca**
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            )

            // Description section
            Text(
                text = merk.deskripsiMerk,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF3C3C3C) // **Teks lebih gelap untuk keterbacaan lebih baik**
                )
            )

            // Divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color.Gray
            )

            // Merk details
            Text(
                text = "ID Merk: ${merk.idMerk}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF3C3C3C) // **Teks lebih gelap untuk konsistensi**
                )
            )
        }
    }
}
