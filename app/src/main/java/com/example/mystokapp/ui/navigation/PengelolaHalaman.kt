package com.example.mystokapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mystokapp.ui.view.DestinasiSplashScreen
import com.example.mystokapp.ui.view.SplashScreen
import com.example.mystokapp.ui.view.kategori.DestinasiKategoriDetail
import com.example.mystokapp.ui.view.kategori.DestinasiKategoriEntry
import com.example.mystokapp.ui.view.kategori.DestinasiKategoriHome
import com.example.mystokapp.ui.view.kategori.DestinasiUpdateKategori
import com.example.mystokapp.ui.view.kategori.DetailKategoriScreen
import com.example.mystokapp.ui.view.kategori.EntryKategoriScreen
import com.example.mystokapp.ui.view.kategori.HomeKategoriScreen
import com.example.mystokapp.ui.view.kategori.UpdateScreenKategori
import com.example.mystokapp.ui.view.merk.DestinasiMerkDetail
import com.example.mystokapp.ui.view.merk.DestinasiMerkEntry
import com.example.mystokapp.ui.view.merk.DestinasiMerkHome
import com.example.mystokapp.ui.view.merk.DestinasiUpdateMerk
import com.example.mystokapp.ui.view.merk.DetailMerkScreen
import com.example.mystokapp.ui.view.merk.EntryMerkScreen
import com.example.mystokapp.ui.view.merk.HomeMerkScreen
import com.example.mystokapp.ui.view.merk.UpdateScreenMerk
import com.example.mystokapp.ui.view.pemasok.DestinasiPemasokDetail
import com.example.mystokapp.ui.view.pemasok.DestinasiPemasokEntry
import com.example.mystokapp.ui.view.pemasok.DestinasiPemasokHome
import com.example.mystokapp.ui.view.pemasok.DestinasiUpdatePemasok
import com.example.mystokapp.ui.view.pemasok.DetailPemasokScreen
import com.example.mystokapp.ui.view.pemasok.EntryPemasokScreen
import com.example.mystokapp.ui.view.pemasok.HomePemasokScreen
import com.example.mystokapp.ui.view.pemasok.UpdateScreenPemasok
import com.example.mystokapp.ui.view.produk.DestinasiDetail
import com.example.mystokapp.ui.view.produk.DestinasiEntry
import com.example.mystokapp.ui.view.produk.DestinasiHome
import com.example.mystokapp.ui.view.produk.DetailScreen
import com.example.mystokapp.ui.view.produk.EntryProdukScreen
import com.example.mystokapp.ui.view.produk.HomeScreen
import com.example.mystokapp.ui.view.produk.UpdateScreenProduk
import com.example.mystokapp.ui.view.produk.DestinasiUpdateProduk

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplashScreen.route,  // Ubah start destination
        modifier = Modifier,
    ) {
        // Home Start Screen
        composable(DestinasiSplashScreen.route) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiSplashScreen.route) { inclusive = true }
                    }
                }
            )
        }
        // Produk Navigation
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToPemasok = { navController.navigate(DestinasiPemasokHome.route) },
                navigateToMerk = { navController.navigate(DestinasiMerkHome.route) },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
                onDetailClick = { idProduk ->
                    navController.navigate("${DestinasiDetail.route}/$idProduk")
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntryProdukScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiDetail.route}/{idProduk}",
            arguments = listOf(navArgument("idProduk") { type = NavType.IntType })
        ) { backStackEntry ->
            val idProduk = backStackEntry.arguments?.getInt("idProduk") ?: 0
            DetailScreen(
                idProduk = idProduk,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = { idProduk ->
                    navController.navigate("${DestinasiUpdateProduk.route}/$idProduk")
                },

                onKategoriClick = { navController.navigate(DestinasiKategoriHome.route) }
            )
        }

        // Edit Produk
        composable(
            route = "${DestinasiUpdateProduk.route}/{idProduk}",
            arguments = listOf(navArgument("idProduk") { type = NavType.IntType })
        ) { backStackEntry ->
            val idProduk = backStackEntry.arguments?.getInt("idProduk") ?: 0
            UpdateScreenProduk(
                idProduk = idProduk,
                onNavigateBack = { navController.navigateUp() }
            )
        }


        // Pemasok Navigation
        composable(DestinasiPemasokHome.route) {
            HomePemasokScreen(
                navigateToPemasokEntry = {
                    navController.navigate(DestinasiPemasokEntry.route)
                },
                onDetailClick = { idPemasok ->
                    navController.navigate("${DestinasiPemasokDetail.route}/$idPemasok")
                },
                navigateToMerk = { navController.navigate(DestinasiMerkHome.route) },
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },

                )
        }

        composable(DestinasiPemasokEntry.route) {
            EntryPemasokScreen(
                navigateBack = {
                    navController.navigate(DestinasiPemasokHome.route) {
                        popUpTo(DestinasiPemasokHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiPemasokDetail.route}/{idPemasok}",
            arguments = listOf(navArgument("idPemasok") { type = NavType.IntType })
        ) { backStackEntry ->
            val idPemasok = backStackEntry.arguments?.getInt("idPemasok") ?: 0
            DetailPemasokScreen(
                idPemasok = idPemasok,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdatePemasok.route}/$idPemasok")
                }
            )
        }

        // Edit Pemasok
        composable(
            route = "${DestinasiUpdatePemasok.route}/{idPemasok}",
            arguments = listOf(navArgument("idPemasok") { type = NavType.IntType })
        ) { backStackEntry ->
            val idPemasok = backStackEntry.arguments?.getInt("idPemasok") ?: 0
            UpdateScreenPemasok(
                idPemasok = idPemasok,
                onNavigateBack = { navController.navigateUp() }
            )
        }


        composable(DestinasiMerkHome.route) {
            HomeMerkScreen(
                navigateToMerkEntry = {
                    navController.navigate(DestinasiMerkEntry.route)
                },
                onDetailClick = { idMerk ->
                    navController.navigate("${DestinasiMerkDetail.route}/$idMerk")
                },
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToPemasok = { navController.navigate(DestinasiPemasokHome.route) },

                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
            )
        }

        composable(DestinasiMerkEntry.route) {
            EntryMerkScreen(
                navigateBack = {
                    navController.navigate(DestinasiMerkHome.route) {
                        popUpTo(DestinasiMerkHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiMerkDetail.route}/{idMerk}",
            arguments = listOf(navArgument("idMerk") { type = NavType.IntType })
        ) { backStackEntry ->
            val idMerk = backStackEntry.arguments?.getInt("idMerk") ?: 0
            DetailMerkScreen(
                idMerk = idMerk,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateMerk.route}/$idMerk")
                }
            )
        }

        // Edit Merk
        composable(
            route = "${DestinasiUpdateMerk.route}/{idMerk}",
            arguments = listOf(navArgument("idMerk") { type = NavType.IntType })
        ) { backStackEntry ->
            val idMerk = backStackEntry.arguments?.getInt("idMerk") ?: 0
            UpdateScreenMerk(
                idMerk = idMerk,
                onNavigateBack = { navController.navigateUp() }
            )
        }


        composable(DestinasiKategoriHome.route) {
            HomeKategoriScreen(
                navigateToKategoriEntry = {
                    navController.navigate(DestinasiKategoriEntry.route)
                },
                onDetailClick = { idMerk ->
                    navController.navigate("${DestinasiKategoriDetail.route}/$idMerk")
                },
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToPemasok = { navController.navigate(DestinasiPemasokHome.route) },

                navigateToMerk = { navController.navigate(DestinasiMerkHome.route) },

                )
        }

        composable(DestinasiKategoriEntry.route) {
            EntryKategoriScreen(
                navigateBack = {
                    navController.navigate(DestinasiKategoriHome.route) {
                        popUpTo(DestinasiKategoriHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiKategoriDetail.route}/{idKategori}",
            arguments = listOf(navArgument("idKategori") { type = NavType.IntType })
        ) { backStackEntry ->
            val idKategori = backStackEntry.arguments?.getInt("idKategori") ?: 0
            DetailKategoriScreen(
                idKategori = idKategori,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateKategori.route}/$idKategori")
                }
            )
        }

        // Edit Kategori
        composable(
            route = "${DestinasiUpdateKategori.route}/{idKategori}",
            arguments = listOf(navArgument("idKategori") { type = NavType.IntType })
        ) { backStackEntry ->
            val idKategori = backStackEntry.arguments?.getInt("idKategori") ?: 0
            UpdateScreenKategori(
                idKategori = idKategori,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
