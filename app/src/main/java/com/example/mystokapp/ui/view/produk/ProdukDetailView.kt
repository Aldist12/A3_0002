package com.example.mystokapp.ui.view.produk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.model.Produk
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.produkVM.DetailProdukUiState
import com.example.mystokapp.ui.viewModel.produkVM.ProdukDetailVM
import kotlinx.serialization.InternalSerializationApi

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail produk"
    override val titleRes = "Detail Produk"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun DetailScreen(
    idProduk: Int,
    onNavigateBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    onKategoriClick: () -> Unit,
    viewModel: ProdukDetailVM = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(idProduk) {
        viewModel.getProdukById(idProduk)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idProduk) },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF6C5CE7), // Warna ungu untuk konsistensi
                contentColor = Color.White,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Produk",
                    tint = Color.White
                )
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailProdukUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailProdukUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color.LightGray) // Menggunakan warna abu-abu terang
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailProdukCard(produk = state.produk)

                    Button(
                        onClick = onKategoriClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6C5CE7), // Warna ungu untuk tombol
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Text(
                            "Lihat Kategori Lainnya",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            is DetailProdukUiState.Error -> {
                Text(
                    text = "Error: ${(state as DetailProdukUiState.Error)}",
                    color = Color.Red, // Menampilkan error dalam warna merah
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun DetailProdukCard(
    produk: Produk,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD0C4FC) // Warna ungu muda agar serasi dengan desain lainnya
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
            // Header section with product name
            Text(
                text = produk.namaProduk,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF2C2C2C), // Warna teks lebih gelap agar lebih jelas
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            )

            // Price section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rp. ${produk.harga}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF3C3C3C) // Warna teks lebih gelap untuk keterbacaan lebih baik
                    )
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFB5A8FF) // Warna stok agar selaras dengan tema
                    )
                ) {
                    Text(
                        text = "Stok: ${produk.stok}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            // Divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color.Gray
            )

            // Product details
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Deskripsi: ${produk.deskripsiProduk}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF3C3C3C)
                    )
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = Color.Gray
                )
                Text(
                    text = "Kategori: ${produk.idKategori}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF3C3C3C)
                    )
                )
                Text(
                    text = "Pemasok: ${produk.idPemasok}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF3C3C3C)
                    )
                )
                Text(
                    text = "Merk: ${produk.idMerk}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF3C3C3C)
                    )
                )
            }
        }
    }
}
