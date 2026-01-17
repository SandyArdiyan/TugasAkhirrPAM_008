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

sealed interface AntrianDetailUiState {
    data class Success(val antrian: Antrian) : AntrianDetailUiState
    object Error : AntrianDetailUiState
    object Loading : AntrianDetailUiState
}

class DetailAntrianViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryAntrian: RepositoryAntrian
) : ViewModel() {

    var antrianDetailState: AntrianDetailUiState by mutableStateOf(AntrianDetailUiState.Loading)
        private set

    // PERBAIKAN DI SINI:
    // Ganti DestinasiDetail.idArg menjadi DestinasiDetail.itemIdArg
    private val _idAntrian: String = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    init {
        getAntrianById()
    }

    fun getAntrianById() {
        viewModelScope.launch {
            antrianDetailState = AntrianDetailUiState.Loading
            antrianDetailState = try {
                val antrian = repositoryAntrian.getAntrianById(_idAntrian)
                AntrianDetailUiState.Success(antrian)
            } catch (e: Exception) { // Ganti IOException jadi Exception biar aman
                e.printStackTrace()
                AntrianDetailUiState.Error
            }
        }
    }
}