package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import com.example.tugasakhirpam.uicontroller.route.DestinasiEdit
import kotlinx.coroutines.launch

class EditAntrianViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryAntrian: RepositoryAntrian
) : ViewModel() {

    var antrianUiState by mutableStateOf(InsertUiState())
        private set

    private val antrianId: String = checkNotNull(savedStateHandle[DestinasiEdit.idArg])

    init {
        viewModelScope.launch {
            antrianUiState = repositoryAntrian.getAntrianById(antrianId).toUiStateAntrian()
        }
    }

    fun updateUiState(detailAntrian: DetailAntrian) {
        antrianUiState = InsertUiState(insertUiEvent = detailAntrian)
    }

    suspend fun updateAntrian() {
        if (validateInput(antrianUiState.insertUiEvent)) {
            repositoryAntrian.updateAntrian(antrianId, antrianUiState.insertUiEvent.toAntrian())
        }
    }

    fun validateInput(uiEvent: DetailAntrian = antrianUiState.insertUiEvent): Boolean {
        return with(uiEvent) {
            namaPasien.isNotBlank() && noRekamMedis.isNotBlank() && poli.isNotBlank()
        }
    }
}