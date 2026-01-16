package com.example.tugasakhirpam

import android.app.Application
import com.example.tugasakhirpam.repositori.AppContainer
import com.example.tugasakhirpam.repositori.ContainerApp

class HospitalApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        // PERBAIKAN: Kurung harus KOSONG. Hapus kata "this".
        container = ContainerApp()
    }
}