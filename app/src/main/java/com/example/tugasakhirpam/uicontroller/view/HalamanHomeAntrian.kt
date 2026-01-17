package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "ANTRIAN RUMAH SAKIT",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
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
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(antrian) { item ->
            AntrianCard(
                antrian = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) }
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
    ElevatedCard(
        modifier = modifier.padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- NOMOR ANTRIAN (Agar tau giliran) ---
            Surface(
                modifier = Modifier.size(54.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = antrian.id,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Nama Pasien
                Text(
                    text = antrian.namaPasien.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Indikator Giliran
                if (antrian.status == "Masuk Ruangan") {
                    Text(
                        text = "• SEDANG DILAYANI",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Poli: ${antrian.poli}", style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Dokter: ${antrian.dokter ?: "-"}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )

                // Status Badge
                Surface(
                    modifier = Modifier.padding(top = 8.dp),
                    color = if (antrian.status == "Masuk Ruangan") Color(0xFFC8E6C9) else Color(0xFFFFECB3),
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Text(
                        text = antrian.status ?: "Menunggu",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (antrian.status == "Masuk Ruangan") Color(0xFF1B5E20) else Color(0xFFE65100)
                    )
                }
            }

            // Tombol Hapus
            IconButton(
                onClick = { onDeleteClick(antrian) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
    }
}