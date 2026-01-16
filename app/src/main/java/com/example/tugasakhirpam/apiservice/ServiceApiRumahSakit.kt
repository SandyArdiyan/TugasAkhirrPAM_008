package com.example.tugasakhirpam.apiservice

import com.example.tugasakhirpam.modeldata.Antrian
import retrofit2.Response
import retrofit2.http.*

interface ServiceApiRumahSakit {
    @GET("getAntrian.php")
    suspend fun getAntrian(): List<Antrian>

    // Must match getAntrianById.php
    @GET("getAntrianById.php")
    suspend fun getAntrianById(@Query("id") id: String): Antrian

    @POST("insertAntrian.php")
    suspend fun insertAntrian(@Body antrian: Antrian)

    // Must match updateAntrian.php
    @POST("updateAntrian.php")
    suspend fun updateAntrian(@Query("id") id: String, @Body antrian: Antrian)

    // Must match deleteAntrian.php
    @GET("deleteAntrian.php")
    suspend fun deleteAntrian(@Query("id") id: String): Response<Void>
}