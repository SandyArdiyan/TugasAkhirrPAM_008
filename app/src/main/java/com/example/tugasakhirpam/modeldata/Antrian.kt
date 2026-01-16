package com.example.tugasakhirpam.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Antrian(
    val id: String,
    val namaPasien: String,
    val noRekamMedis: String,
    val poli: String,
    val alamat: String,
    val tanggal: String,
    val dokter: String? = null,

    // PERBAIKAN: Tambahkan ? dan = null
    val status: String? = null
)