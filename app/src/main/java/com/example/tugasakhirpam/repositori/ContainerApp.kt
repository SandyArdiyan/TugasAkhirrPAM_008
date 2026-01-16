package com.example.tugasakhirpam.repositori

import com.example.tugasakhirpam.apiservice.ServiceApiRumahSakit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val repositoryAntrian: RepositoryAntrian
}

class ContainerApp : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/" // Ganti dengan URL API Anda
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val serviceApiRumahSakit: ServiceApiRumahSakit by lazy {
        retrofit.create(ServiceApiRumahSakit::class.java)
    }

    override val repositoryAntrian: RepositoryAntrian by lazy {
        NetworkRepositoryAntrian(serviceApiRumahSakit)
    }
}