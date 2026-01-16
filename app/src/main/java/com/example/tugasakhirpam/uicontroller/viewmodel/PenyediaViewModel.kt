package com.example.tugasakhirpam.uicontroller.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpam.HospitalApp
import com.example.tugasakhirpam.uicontroller.viewmodel.AntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.DetailAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.EditAntrianViewModel
import com.example.tugasakhirpam.uicontroller.viewmodel.EntryAntrianViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            AntrianViewModel(aplikasiRumahSakit().container.repositoryAntrian)
        }

        initializer {
            EntryAntrianViewModel(aplikasiRumahSakit().container.repositoryAntrian)
        }

        initializer {
            DetailAntrianViewModel(
                createSavedStateHandle(),
                aplikasiRumahSakit().container.repositoryAntrian
            )
        }

        initializer {
            EditAntrianViewModel(
                createSavedStateHandle(),
                aplikasiRumahSakit().container.repositoryAntrian
            )
        }
    }
}

// Extension function untuk mengakses Application Container
fun CreationExtras.aplikasiRumahSakit(): HospitalApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HospitalApp)