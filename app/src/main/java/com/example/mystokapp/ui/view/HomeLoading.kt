package com.example.mystokapp.ui.view


import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mystokapp.ui.navigation.DestinasiNavigasi
import com.example.mystokapp.R

import kotlinx.coroutines.delay

object DestinasiSplashScreen : DestinasiNavigasi {
    override val route = "splash_screen"
    override val titleRes = "Splash Screen"
}

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        )
    )

    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navigateToHome()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFF5F6FF)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .scale(scaleAnim.value)
                    .alpha(alphaAnim.value)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Aplikasi",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(8.dp),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "My Stokk App",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF6C5CE7), // Teks ungu
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Kelola Stok Barang Anda",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF6C5CE7).copy(alpha = 0.7f), // Teks ungu dengan transparansi
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
