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

    private val itemId: String = checkNotNull(savedStateHandle[DestinasiEdit.idArg])

    init {
        viewModelScope.launch {
            // Ambil data dari server dan masukkan ke form edit
            val antrian = repositoryAntrian.getAntrianById(itemId)
            antrianUiState = antrian.toUiStateAntrian()
        }
    }

    fun updateUiState(detailAntrian: DetailAntrian) {
        antrianUiState = InsertUiState(insertUiEvent = detailAntrian)
    }

    suspend fun updateAntrian() {
        // Gunakan fungsi update yang ada di repository
        // Pastikan DetailAntrian.toAntrian() sudah membawa dokter & status (dari file EntryAntrianViewModel)
        repositoryAntrian.updateAntrian(itemId, antrianUiState.insertUiEvent.toAntrian())
    }
}