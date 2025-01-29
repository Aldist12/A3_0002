package com.example.mystokapp.ui.view.merk


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.model.Merk
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.merkVM.HomeUiState
import com.example.mystokapp.ui.viewModel.merkVM.MerkHomeVM
import kotlinx.serialization.InternalSerializationApi


object DestinasiMerkHome : DestinasiNavigasi {
    override val route = "home merk"
    override val titleRes = "Home Merk"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun HomeMerkScreen(
    navigateToMerkEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onBack: () -> Unit = {},
    navigateToPemasok: () -> Unit,
    navigateToKategori: () -> Unit,
    viewModel: MerkHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Kelola Nama Brand",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.Gray
                        )
                        Text(
                            text = "Pengelolaan Nama Merk",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getMerk() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = Color(0xFF6C5CE7)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF0F0F0))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToMerkEntry,
                shape = RoundedCornerShape(16.dp),
                containerColor = Color(0xFF6C5CE7),
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Brand")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Merk")
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Products"
                        )
                    },
                    label = { Text("Produk") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        onBack()
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Categories"
                        )
                    },
                    label = { Text("Kategori") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        navigateToKategori()
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Suppliers"
                        )
                    },
                    label = { Text("Pemasok") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        navigateToPemasok()
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Brands"
                        )
                    },
                    label = { Text("Merk") },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                    }
                )
            }
        },
        containerColor = Color(0xFFF5F6FF)
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.merkUiState,
            retryAction = { viewModel.getMerk() },
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteMerk(it.idMerk)
                viewModel.getMerk()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun MerkCard(
    merk: Merk,
    modifier: Modifier = Modifier,
    onDeleteClick: (Merk) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFF6C5CE7).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFF6C5CE7)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = merk.namaMerk,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            IconButton(
                onClick = { onDeleteClick(merk) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun MerkLayout(
    merk: List<Merk>,
    modifier: Modifier = Modifier,
    onDetailClick: (Merk) -> Unit,
    onDeleteClick: (Merk) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(merk) { item ->
            MerkCard(
                merk = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFF6C5CE7)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tidak ada data Merk",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C5CE7)
            )
        ) {
            Text("Retry", color = Color.White)
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Merk) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.merk.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data Merk",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                MerkLayout(
                    merk = homeUiState.merk,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { merk -> onDetailClick(merk.idMerk) },
                    onDeleteClick = onDeleteClick
                )
            }
        }

        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}
