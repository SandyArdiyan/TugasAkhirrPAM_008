package com.example.tugasakhirpam.uicontroller.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.uicontroller.route.DestinasiEdit
import com.example.tugasakhirpam.uicontroller.viewmodel.EditAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEditAntrian(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: EditAntrianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            HospitalTopAppBar(
                title = DestinasiEdit.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.antrianUiState,
            onAntrianValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAntrian()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}