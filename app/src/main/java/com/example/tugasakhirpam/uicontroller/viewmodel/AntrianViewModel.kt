package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AntrianUiState {
    data class Success(val antrian: List<Antrian>) : AntrianUiState
    object Error : AntrianUiState
    object Loading : AntrianUiState
}

class AntrianViewModel(private val repositoryAntrian: RepositoryAntrian) : ViewModel() {
    var antrianUiState: AntrianUiState by mutableStateOf(AntrianUiState.Loading)
        private set

    init {
        getAntrian()
    }

    fun getAntrian() {
        viewModelScope.launch {
            antrianUiState = AntrianUiState.Loading
            antrianUiState = try {
                AntrianUiState.Success(repositoryAntrian.getAntrian())
            } catch (e: IOException) {
                AntrianUiState.Error
            }
        }
    }

    fun deleteAntrian(id: String) {
        viewModelScope.launch {
            try {
                repositoryAntrian.deleteAntrian(id)
                getAntrian() // Refresh data
            } catch (e: IOException) {
                antrianUiState = AntrianUiState.Error
            }
        }
    }
}