package com.example.mystokapp.ui.view.kategori

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
                onClick = {onEditClick(idKategori)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kategori",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
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
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailKategoriCard(kategori = state.kategori)
                }
            }
            is DetailKategoriUiState.Error -> OnError(retryAction = { viewModel.getKategoriById(idKategori) })
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
            // Header section with kategori name
            Text(
                text = kategori.namaKategori,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )

            // Description section
            Text(
                text = kategori.deskripsiKategori,
                style = MaterialTheme.typography.bodyLarge
            )

            // Divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Kategori details
            Text(
                text = "ID Kategori: ${kategori.idKategori}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}