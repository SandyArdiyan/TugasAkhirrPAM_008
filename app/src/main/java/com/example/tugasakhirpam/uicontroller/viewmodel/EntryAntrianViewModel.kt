package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.repositori.RepositoryAntrian
import kotlinx.coroutines.launch

class EntryAntrianViewModel(private val repositoryAntrian: RepositoryAntrian) : ViewModel() {
    var uiStateAntrian by mutableStateOf(InsertUiState())
        private set

    fun updateUiState(detailAntrian: DetailAntrian) {
        uiStateAntrian = InsertUiState(insertUiEvent = detailAntrian)
    }

    suspend fun saveAntrian() {
        if (validateInput()) {
            repositoryAntrian.insertAntrian(uiStateAntrian.insertUiEvent.toAntrian())
        }
    }

    fun validateInput(uiEvent: DetailAntrian = uiStateAntrian.insertUiEvent): Boolean {
        return with(uiEvent) {
            namaPasien.isNotBlank() && noRekamMedis.isNotBlank() && poli.isNotBlank()
        }
    }
}

data class InsertUiState(
    val insertUiEvent: DetailAntrian = DetailAntrian()
)

data class DetailAntrian(
    val id: String = "",
    val namaPasien: String = "",
    val noRekamMedis: String = "",
    val alamat: String = "",
    val poli: String = "",
    val tanggal: String = ""
)

// Konversi dari UI State ke Model Data
fun DetailAntrian.toAntrian(): Antrian = Antrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,
    tanggal = tanggal
)

// Konversi dari Model Data ke UI State (untuk Edit/Detail)
fun Antrian.toDetailAntrian(): DetailAntrian = DetailAntrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,
    tanggal = tanggal
)

fun Antrian.toUiStateAntrian(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailAntrian()
)