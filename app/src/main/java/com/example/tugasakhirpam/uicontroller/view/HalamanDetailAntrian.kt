package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.uicontroller.route.DestinasiDetail
import com.example.tugasakhirpam.uicontroller.viewmodel.DetailAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.DetailUiState
import com.example.tugasakhirpam.uicontroller.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetailAntrian(
    navigateBack: () -> Unit,
    navigateToEditItem: (String) -> Unit,
    viewModel: DetailAntrianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.detailUiState.collectAsState()

    Scaffold(
        topBar = {
            HospitalTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            if (uiState is DetailUiState.Success) {
                FloatingActionButton(
                    onClick = { navigateToEditItem((uiState as DetailUiState.Success).antrian.id) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(18.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Antrian")
                }
            }
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is DetailUiState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            is DetailUiState.Error -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Gagal memuat data.")
            }
            is DetailUiState.Success -> {
                ItemDetailBody(
                    antrian = state.antrian,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun ItemDetailBody(
    antrian: Antrian,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailRow(label = "Nama Pasien", value = antrian.namaPasien)
            DetailRow(label = "No. Rekam Medis", value = antrian.noRekamMedis)
            DetailRow(label = "Poli Tujuan", value = antrian.poli)
            DetailRow(label = "Alamat", value = antrian.alamat)
            DetailRow(label = "Tanggal", value = antrian.tanggal)
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}