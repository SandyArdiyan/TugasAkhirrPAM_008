package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import com.example.tugasakhirpam.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

// UI State untuk Detail
sealed interface AntrianDetailUiState {
    data class Success(val antrian: Antrian) : AntrianDetailUiState
    object Error : AntrianDetailUiState
    object Loading : AntrianDetailUiState
}

class DetailAntrianViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryAntrian: RepositoryAntrian
) : ViewModel() {

    // Menangkap ID dari argumen navigasi
    private val _idAntrian: String = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    // State untuk UI (Loading, Success, Error)
    var antrianDetailState: AntrianDetailUiState by mutableStateOf(AntrianDetailUiState.Loading)
        private set

    // Fungsi untuk mengambil data by ID
    fun getAntrianById() {
        viewModelScope.launch {
            antrianDetailState = AntrianDetailUiState.Loading
            antrianDetailState = try {
                val antrian = repositoryAntrian.getAntrianById(_idAntrian)
                AntrianDetailUiState.Success(antrian)
            } catch (e: IOException) {
                AntrianDetailUiState.Error
            } catch (e: Exception) {
                AntrianDetailUiState.Error
            }
        }
    }
}