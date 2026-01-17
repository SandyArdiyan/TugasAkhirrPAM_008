package com.example.tugasakhirpam.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Antrian(
    val id: String,
    val namaPasien: String,
    val noRekamMedis: String,
    val alamat: String,
    val poli: String,
    val dokter: String? = null,
    val tanggal: String,
    val status: String? = null
)

// UI State untuk Form
data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isEntryValid: Boolean = false
)

data class InsertUiEvent(
    val id: String = "",
    val namaPasien: String = "",
    val noRekamMedis: String = "",
    val alamat: String = "",
    val poli: String = "",
    val dokter: String = "",
    val tanggal: String = "",
    val status: String = "Menunggu"
)

// Fungsi Ekstensi (PENTING untuk memperbaiki error compile)
fun Antrian.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,
    dokter = dokter ?: "",
    tanggal = tanggal,
    status = status ?: "Menunggu"
)

fun Antrian.toInsertUiState(): InsertUiState = InsertUiState(
    insertUiEvent = this.toInsertUiEvent(),
    isEntryValid = true
)

fun InsertUiEvent.toAntrian(): Antrian = Antrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,
    dokter = dokter,
    tanggal = tanggal,
    status = status
)