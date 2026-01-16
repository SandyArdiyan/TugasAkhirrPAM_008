package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import com.example.tugasakhirpam.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface DetailUiState {
    data class Success(val antrian: Antrian) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailAntrianViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryAntrian: RepositoryAntrian
) : ViewModel() {

    private val antrianId: String = checkNotNull(savedStateHandle[DestinasiDetail.idArg])

    val detailUiState: StateFlow<DetailUiState> =
        kotlinx.coroutines.flow.flow {
            emit(DetailUiState.Loading)
            try {
                val antrian = repositoryAntrian.getAntrianById(antrianId)
                emit(DetailUiState.Success(antrian))
            } catch (e: Exception) {
                emit(DetailUiState.Error)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState.Loading
        )
}