package com.example.tugasakhirpam.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tugasakhirpam.uicontroller.route.DestinasiDetail
import com.example.tugasakhirpam.uicontroller.route.DestinasiEdit
import com.example.tugasakhirpam.uicontroller.route.DestinasiEntry
import com.example.tugasakhirpam.uicontroller.route.DestinasiHome
import com.example.tugasakhirpam.uicontroller.view.HalamanHomeAntrian
import com.example.tugasakhirpam.uicontroller.view.HalamanEntryAntrian
import com.example.tugasakhirpam.uicontroller.view.HalamanDetailAntrian
import com.example.tugasakhirpam.uicontroller.view.HalamanEditAntrian

@Composable
fun PetaNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // --- HALAMAN HOME ---
        composable(DestinasiHome.route) {
            HalamanHomeAntrian(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                }
            )
        }

        // --- HALAMAN ENTRY (TAMBAH DATA) ---
        composable(DestinasiEntry.route) {
            HalamanEntryAntrian(
                navigateBack = { navController.popBackStack() }
            )
        }

        // --- HALAMAN DETAIL ---
        composable(
            route = DestinasiDetail.routeWithArgs,
            // PERBAIKAN 1: Gunakan itemIdArg
            arguments = listOf(navArgument(DestinasiDetail.itemIdArg) { type = NavType.StringType })
        ) {
            // PERBAIKAN 2: Gunakan itemIdArg
            val antrianId = it.arguments?.getString(DestinasiDetail.itemIdArg)
            antrianId?.let { id ->
                HalamanDetailAntrian(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = { navController.navigate("${DestinasiEdit.route}/$id") }
                )
            }
        }

        // --- HALAMAN EDIT ---
        composable(
            route = DestinasiEdit.routeWithArgs,
            // PERBAIKAN 3: Gunakan itemIdArg
            arguments = listOf(navArgument(DestinasiEdit.itemIdArg) { type = NavType.StringType })
        ) {
            HalamanEditAntrian(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}