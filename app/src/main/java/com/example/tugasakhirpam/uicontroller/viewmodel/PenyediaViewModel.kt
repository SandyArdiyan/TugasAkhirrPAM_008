package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpam.HospitalApp // Pastikan ini tidak merah. Kalau merah, ganti dengan nama file Application kamu.

object PenyediaViewModel {
    val Factory = viewModelFactory {

        // 1. Initializer untuk Home
        initializer {
            AntrianViewModel(aplikasiRumahSakit().container.repositoryAntrian)
        }

        // 2. Initializer untuk Entry (Tambah Data)
        initializer {
            EntryAntrianViewModel(aplikasiRumahSakit().container.repositoryAntrian)
        }

        // 3. Initializer untuk Detail
        initializer {
            DetailAntrianViewModel(
                createSavedStateHandle(),
                aplikasiRumahSakit().container.repositoryAntrian
            )
        }

        // 4. Initializer untuk Edit
        initializer {
            EditAntrianViewModel(
                createSavedStateHandle(),
                aplikasiRumahSakit().container.repositoryAntrian
            )
        }
    }
}

// Extension function untuk mengakses Application Container
// Nama fungsi ini "aplikasiRumahSakit", jadi di atas harus panggil "aplikasiRumahSakit" juga.
fun CreationExtras.aplikasiRumahSakit(): HospitalApp = // <--- HospitalApp
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HospitalApp) // <--- HospitalApp