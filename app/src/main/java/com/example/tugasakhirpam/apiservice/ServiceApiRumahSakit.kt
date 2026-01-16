package com.example.tugasakhirpam.apiservice

import com.example.tugasakhirpam.modeldata.Antrian
import retrofit2.Response // Pastikan import Response
import retrofit2.http.*

interface ServiceApiRumahSakit {

    // --- KODE LAMA (INSERT & GET ALL) ---
    @GET("getAntrian.php")
    suspend fun getAntrian(): List<Antrian>

    @Headers("Content-Type: application/json")
    @POST("insertAntrian.php")
    suspend fun insertAntrian(@Body antrian: Antrian)

    // --- TAMBAHAN BARU (WAJIB ADA UNTUK EDIT) ---

    // 1. Ambil 1 data berdasarkan ID (agar form edit terisi otomatis)
    @GET("getAntrianById.php")
    suspend fun getAntrianById(@Query("id") id: String): Antrian

    // 2. Update data (mengirim data revisi ke server)
    @Headers("Content-Type: application/json")
    @POST("updateAntrian.php") // Pakai POST biar aman di PHP
    suspend fun updateAntrian(@Query("id") id: String, @Body antrian: Antrian)

    // 3. Delete data
    @GET("deleteAntrian.php")
    suspend fun deleteAntrian(@Query("id") id: String): Response<Void>
}