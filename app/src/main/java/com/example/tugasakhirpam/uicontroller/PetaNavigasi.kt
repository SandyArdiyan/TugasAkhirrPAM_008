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
// Pastikan import file halaman lain (Entry, Detail, Edit) jika sudah dibuat
// import com.example.tugasakhirpam.uicontroller.view.HalamanEntryAntrian
// import com.example.tugasakhirpam.uicontroller.view.HalamanDetailAntrian
// import com.example.tugasakhirpam.uicontroller.view.HalamanEditAntrian

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
        composable(DestinasiHome.route) {
            HalamanHomeAntrian(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                }
            )
        }

        composable(DestinasiEntry.route) {
            // HalamanEntryAntrian(navigateBack = { navController.popBackStack() })
            // Uncomment dan sesuaikan parameter jika HalamanEntryAntrian sudah siap
        }

        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.idArg) { type = NavType.StringType })
        ) {
            // HalamanDetailAntrian(...)
        }

        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.idArg) { type = NavType.StringType })
        ) {
            // HalamanEditAntrian(...)
        }
    }
}