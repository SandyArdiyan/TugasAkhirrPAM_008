package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.uicontroller.route.DestinasiHome
import com.example.tugasakhirpam.uicontroller.viewmodel.AntrianUiState
import com.example.tugasakhirpam.uicontroller.viewmodel.AntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHomeAntrian(
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit,
    viewModel: AntrianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getAntrian()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            HospitalTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Antrian")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            antrianUiState = viewModel.antrianUiState,
            retryAction = viewModel::getAntrian,
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteAntrian(it.id)
            }
        )
    }
}

@Composable
fun HomeStatus(
    antrianUiState: AntrianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Antrian) -> Unit,
    onDetailClick: (String) -> Unit
) {
    when (antrianUiState) {
        is AntrianUiState.Loading -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is AntrianUiState.Success ->
            if (antrianUiState.antrian.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data antrian")
                }
            } else {
                AntrianLayout(
                    antrian = antrianUiState.antrian,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        is AntrianUiState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Gagal memuat data")
                Button(onClick = retryAction) {
                    Text(text = "Coba Lagi")
                }
            }
        }
    }
}

@Composable
fun AntrianLayout(
    antrian: List<Antrian>,
    modifier: Modifier = Modifier,
    onDetailClick: (Antrian) -> Unit,
    onDeleteClick: (Antrian) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(antrian) { kontak ->
            AntrianCard(
                antrian = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kontak) },
                onDeleteClick = { onDeleteClick(kontak) }
            )
        }
    }
}

@Composable
fun AntrianCard(
    antrian: Antrian,
    modifier: Modifier = Modifier,
    onDeleteClick: (Antrian) -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Nama Pasien
                Text(
                    text = antrian.namaPasien,
                    style = MaterialTheme.typography.titleLarge
                )

                // Poli
                Text(
                    text = "Poli: ${antrian.poli}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Dokter: ${antrian.dokter ?: "-"}", // Pakai ?: "-" biar aman
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold // Saya tebalkan sedikit biar jelas
                )

                Text(
                    text = "Status: ${antrian.status ?: "Menunggu"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                // No RM
                Text(
                    text = "No RM: ${antrian.noRekamMedis}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            IconButton(onClick = { onDeleteClick(antrian) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}