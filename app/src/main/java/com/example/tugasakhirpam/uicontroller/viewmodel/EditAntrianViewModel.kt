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

    // Mengambil ID dari navigasi (dikirim saat tombol Edit diklik)
    private val _idAntrian: String = checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])

    init {
        // Otomatis ambil data lama saat halaman dibuka
        viewModelScope.launch {
            try {
                val antrian = repositoryAntrian.getAntrianById(_idAntrian)
                // Konversi data database ke tampilan UI
                antrianUiState = antrian.toUiStateAntrian()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi update tampilan saat user mengetik
    fun updateUiState(detailAntrian: DetailAntrian) {
        antrianUiState = InsertUiState(insertUiEvent = detailAntrian)
    }

    // Fungsi Simpan Perubahan ke Database
    suspend fun updateAntrian() {
        try {
            repositoryAntrian.updateAntrian(_idAntrian, antrianUiState.insertUiEvent.toAntrian())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}