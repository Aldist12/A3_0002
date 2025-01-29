package com.example.mystokapp.ui.view.kategori

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.model.Kategori
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.kategoriVM.DetailKategoriUiState
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriDetailVM
import kotlinx.serialization.InternalSerializationApi


object DestinasiKategoriDetail : DestinasiNavigasi {
    override val route = "detail kategori"
    override val titleRes = "Detail Kategori"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun DetailKategoriScreen(
    idKategori: Int = 0,
    onNavigateBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: KategoriDetailVM = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(idKategori) {
        viewModel.getKategoriById(idKategori)
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiKategoriDetail.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idKategori) },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF6C5CE7), // Warna tombol tetap ungu
                contentColor = Color.White,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kategori",
                    tint = Color.White
                )
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailKategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailKategoriUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color.LightGray) // **Menggunakan warna putih polos**
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailKategoriCard(kategori = state.kategori)
                }
            }

            is DetailKategoriUiState.Error -> OnError(retryAction = {
                viewModel.getKategoriById(idKategori)
            })
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun DetailKategoriCard(
    kategori: Kategori,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD0C4FC) // Warna card tetap ungu muda
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
            // Header kategori
            Text(
                text = kategori.namaKategori,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF2C2C2C), // Warna teks lebih gelap agar kontras lebih baik
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            )

            // Deskripsi kategori
            Text(
                text = kategori.deskripsiKategori,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF3C3C3C) // Warna teks lebih gelap agar lebih mudah dibaca
                )
            )

            // Divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color.Gray
            )

            // Detail ID Kategori
            Text(
                text = "ID Kategori: ${kategori.idKategori}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF3C3C3C) // Warna teks lebih gelap agar lebih jelas
                )
            )
        }
    }
}
