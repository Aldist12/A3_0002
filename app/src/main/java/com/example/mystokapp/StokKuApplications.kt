package com.example.mystokapp

import android.app.Application
import com.example.mystokapp.dependenciesinject.AppContainer
import com.example.mystokapp.dependenciesinject.ProdukContainer


class StokKuApplications : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ProdukContainer()
    }
}