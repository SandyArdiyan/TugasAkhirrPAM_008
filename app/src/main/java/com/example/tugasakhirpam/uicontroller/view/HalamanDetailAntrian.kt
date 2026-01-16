package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.uicontroller.route.DestinasiDetail
import com.example.tugasakhirpam.uicontroller.viewmodel.AntrianDetailUiState
import com.example.tugasakhirpam.uicontroller.viewmodel.DetailAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetailAntrian(
    navigateBack: () -> Unit,
    navigateToEditItem: () -> Unit,
    viewModel: DetailAntrianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getAntrianById()
    }

    Scaffold(
        topBar = {
            HospitalTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEditItem,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Antrian")
            }
        },
    ) { innerPadding ->
        DetailStatus(
            antrianUiState = viewModel.antrianDetailState,
            retryAction = viewModel::getAntrianById,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun DetailStatus(
    antrianUiState: AntrianDetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (antrianUiState) {
        is AntrianDetailUiState.Loading -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        is AntrianDetailUiState.Success -> ItemDetailBody(antrian = antrianUiState.antrian, modifier = modifier)
        is AntrianDetailUiState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Gagal memuat data") }
    }
}

@Composable
fun ItemDetailBody(antrian: Antrian, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                DetailRow(label = "Nama", value = antrian.namaPasien)
                DetailRow(label = "No RM", value = antrian.noRekamMedis)
                DetailRow(label = "Poli", value = antrian.poli)

                // PERBAIKAN 1: Menangani Dokter Kosong
                DetailRow(label = "Dokter", value = antrian.dokter ?: "-")

                // PERBAIKAN 2: Menangani Status Kosong (Supaya tidak merah lagi)
                DetailRow(label = "Status", value = antrian.status ?: "Menunggu")
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}