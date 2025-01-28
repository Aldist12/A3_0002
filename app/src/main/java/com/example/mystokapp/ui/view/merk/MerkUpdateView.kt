package com.example.mystokapp.ui.view.merk


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is UpdateMerkUiState.Loading -> CircularProgressIndicator()
                is UpdateMerkUiState.Success -> {
                    val merk = (uiState as UpdateMerkUiState.Success).merk
                    com.example.mystokapp.ui.view.merk.UpdateFormMerk(
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
                is UpdateMerkUiState.Error -> {
                    Text("Error: ${(uiState as UpdateMerkUiState.Error).message}")
                }
                else -> {}
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
            label = { Text("Nama Merk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsiMerkState,
            onValueChange = { deskripsiMerkState = it },
            label = { Text("Deskripsi Merk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}