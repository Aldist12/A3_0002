package com.example.mystokapp.ui.view.produk


import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mystokapp.model.Produk
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.ui.viewModel.PenyediaViewModel
import com.example.mystokapp.ui.viewModel.produkVM.HomeUiState
import com.example.mystokapp.ui.viewModel.produkVM.ProdukHomeVM
import kotlinx.serialization.InternalSerializationApi
import kotlin.collections.forEach
import com.example.mystokapp.R


object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Produk"
}

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToPemasok: () -> Unit,
    navigateToMerk: () -> Unit,
    navigateToKategori: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: ProdukHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Halo, Selamat Datang 👋",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                            Text(
                                text = "Beranda",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.getProduk() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    }
                )

                // Search Bar
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Cari produk...") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            singleLine = true
                        )
                    }
                }

                // Statistics Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF6C5CE7)
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.banner), // Replace with your drawable resource
                        contentDescription = null,
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = RoundedCornerShape(16.dp),
                containerColor = Color(0xFF6C5CE7),
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Produk")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Produk")
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, "Produk") },
                    label = { Text("Produk") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, "Kategori") },
                    label = { Text("Kategori") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        navigateToKategori()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ExitToApp, "Pemasok") },
                    label = { Text("Pemasok") },
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        navigateToPemasok()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, "Merk") },
                    label = { Text("Merk") },
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        navigateToMerk()
                    }
                )
            }
        },
        containerColor = Color(0xFFF5F6FF)
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.produkUiState,
            retryAction = { viewModel.getProduk() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { produk ->
                viewModel.deleteProduk(produk.idProduk)
                viewModel.getProduk()
            }
        )
    }
}

@Composable
private fun StatisticItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun HeaderIconButton(
    icon: ImageVector,
    contentDescription: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .background(
                color = backgroundColor.copy(alpha = 0.1f),
                shape = CircleShape
            )
    ) {
        Icon(icon, contentDescription = contentDescription, tint = backgroundColor)
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Produk) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.produk.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data produk",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                ProdukLayout(
                    produk = homeUiState.produk,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idProduk) },
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun ProdukLayout(
    produk: List<Produk>,
    modifier: Modifier = Modifier,
    onDetailClick: (Produk) -> Unit,
    onDeleteClick: (Produk) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        items(produk.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { item ->
                    ProdukCard(
                        produk = item,
                        modifier = Modifier.weight(1f),
                        onDetailClick = onDetailClick,
                        onDeleteClick = onDeleteClick
                    )
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun ProdukCard(
    produk: Produk,
    modifier: Modifier = Modifier,
    onDetailClick: (Produk) -> Unit = {}, // Tambahkan onDetailClick di sini
    onDeleteClick: (Produk) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .height(220.dp)
            .clickable { onDetailClick(produk) }, // Hubungkan klik ke onDetailClick
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = produk.namaProduk,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${produk.stok} Stock",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rp.${produk.harga}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    FloatingActionButton(
                        onClick = { onDeleteClick(produk) },
                        modifier = Modifier.size(32.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = stringResource(R.string.error),
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(R.string.loading_failed),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.error
            )
        )
        Button(
            onClick = retryAction,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.retry), color = Color.White)
        }
    }
}
