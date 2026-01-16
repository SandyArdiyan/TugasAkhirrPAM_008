package com.example.tugasakhirpam.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Antrian(
    val id: String, // Menggunakan String agar fleksibel (bisa UUID atau Int)
    val namaPasien: String,
    val noRekamMedis: String,
    val alamat: String,
    val poli: String,
    val tanggal: String
)