package com.example.mystokapp.ui.view.pemasok

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.model.Pemasok
import com.example.mystokapp.ui.customWidget.CostumeTopAppBar
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.pemasokVM.DetailPemasokUiState
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokDetailVM
import kotlinx.serialization.InternalSerializationApi

object DestinasiPemasokDetail : DestinasiNavigasi {
    override val route = "detail pemasok"
    override val titleRes = "Detail Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun DetailPemasokScreen(
    idPemasok: Int,
    onNavigateBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: PemasokDetailVM = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()


    LaunchedEffect(idPemasok) {
        viewModel.getPemasokById(idPemasok)
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPemasokDetail.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idPemasok) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pemasok",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailPemasokUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailPemasokUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailPemasokCard(pemasok = state.pemasok)
                }
            }

            is DetailPemasokUiState.Error -> OnError(retryAction = {
                viewModel.getPemasokById(
                    idPemasok
                )
            })
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun DetailPemasokCard(
    pemasok: Pemasok,
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
            // Header section with supplier name
            Text(
                text = pemasok.namaPemasok,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )

            // Address section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pemasok.alamatPemasok,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Supplier details
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Telepon: ${pemasok.teleponPemasok}",
                    style = MaterialTheme.typography.titleMedium
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Text(
                    text = "ID Pemasok: ${pemasok.idPemasok}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
