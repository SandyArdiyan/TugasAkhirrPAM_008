package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tugasakhirpam.modeldata.Antrian
import com.example.tugasakhirpam.repositori.RepositoryAntrian

// 1. DATA CLASS UNTUK UI (Wajib String, tidak boleh null)
data class DetailAntrian(
    val id: String = "",
    val namaPasien: String = "",
    val noRekamMedis: String = "",
    val alamat: String = "",
    val poli: String = "",
    val dokter: String = "",
    val tanggal: String = "",
    val status: String = "Menunggu" // Default value
)

// 2. KONVERSI DARI UI KE DATA MODEL (Untuk Simpan ke Database)
fun DetailAntrian.toAntrian(): Antrian = Antrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,
    dokter = dokter,
    tanggal = tanggal,
    status = status
)

// 3. KONVERSI DARI DATA MODEL KE UI (Untuk Menampilkan Data)
// BAGIAN PENTING: Menggunakan ?: "" agar tidak crash jika data dari server kosong
fun Antrian.toDetailAntrian(): DetailAntrian = DetailAntrian(
    id = id,
    namaPasien = namaPasien,
    noRekamMedis = noRekamMedis,
    alamat = alamat,
    poli = poli,

    // Perbaikan: Kalau dokter kosong, ganti jadi teks kosong
    dokter = dokter ?: "",

    tanggal = tanggal,

    // Perbaikan: Kalau status kosong, ganti jadi "Menunggu"
    status = status ?: "Menunggu"
)

// 4. Wrapper State
fun Antrian.toUiStateAntrian(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailAntrian()
)

data class InsertUiState(
    val insertUiEvent: DetailAntrian = DetailAntrian()
)

// 5. VIEW MODEL UTAMA
class EntryAntrianViewModel(private val repositoryAntrian: RepositoryAntrian) : ViewModel() {
    var uiStateAntrian by mutableStateOf(InsertUiState())
        private set

    // State untuk menangani Error/Sukses
    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isSuccess by mutableStateOf(false)

    // Update data saat user mengetik
    fun updateUiState(detailAntrian: DetailAntrian) {
        uiStateAntrian = InsertUiState(insertUiEvent = detailAntrian)
    }

    // Fungsi Simpan Data (Create)
    suspend fun saveAntrian() {
        if (validateInput()) {
            try {
                // Reset status error sebelum mencoba menyimpan
                isError = false
                errorMessage = ""

                repositoryAntrian.insertAntrian(uiStateAntrian.insertUiEvent.toAntrian())

                // Jika berhasil
                isSuccess = true
            } catch (e: Exception) {
                e.printStackTrace()
                isError = true
                errorMessage = "Gagal menyimpan: ${e.message}"
            }
        } else {
            isError = true
            errorMessage = "Data tidak boleh kosong!"
        }
    }

    // Validasi Form (Tidak boleh kosong)
    private fun validateInput(uiState: DetailAntrian = uiStateAntrian.insertUiEvent): Boolean {
        return with(uiState) {
            namaPasien.isNotBlank() && noRekamMedis.isNotBlank() && poli.isNotBlank() && dokter.isNotBlank()
        }
    }
}