package com.example.tugasakhirpam.apiservice

import com.example.tugasakhirpam.modeldata.Antrian
import retrofit2.Response
import retrofit2.http.*

interface ServiceApiRumahSakit {
    @GET("antrian")
    suspend fun getAntrian(): List<Antrian>

    @GET("antrian/{id}")
    suspend fun getAntrianById(@Path("id") id: String): Antrian

    @POST("antrian")
    suspend fun insertAntrian(@Body antrian: Antrian)

    @PUT("antrian/{id}")
    suspend fun updateAntrian(@Path("id") id: String, @Body antrian: Antrian)

    @DELETE("antrian/{id}")
    suspend fun deleteAntrian(@Path("id") id: String): Response<Void>
}