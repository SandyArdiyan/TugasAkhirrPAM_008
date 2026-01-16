package com.example.tugasakhirpam.repositori

import com.example.tugasakhirpam.apiservice.ServiceApiRumahSakit
import com.example.tugasakhirpam.modeldata.Antrian
import java.io.IOException

interface RepositoryAntrian {
    suspend fun getAntrian(): List<Antrian>
    suspend fun getAntrianById(id: String): Antrian
    suspend fun insertAntrian(antrian: Antrian)
    suspend fun updateAntrian(id: String, antrian: Antrian)
    suspend fun deleteAntrian(id: String)
}

class NetworkRepositoryAntrian(
    private val serviceApiRumahSakit: ServiceApiRumahSakit
) : RepositoryAntrian {
    override suspend fun getAntrian(): List<Antrian> = serviceApiRumahSakit.getAntrian()
    override suspend fun getAntrianById(id: String): Antrian = serviceApiRumahSakit.getAntrianById(id)
    override suspend fun insertAntrian(antrian: Antrian) = serviceApiRumahSakit.insertAntrian(antrian)
    override suspend fun updateAntrian(id: String, antrian: Antrian) = serviceApiRumahSakit.updateAntrian(id, antrian)
    override suspend fun deleteAntrian(id: String) {
        try {
            val response = serviceApiRumahSakit.deleteAntrian(id)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus data: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}