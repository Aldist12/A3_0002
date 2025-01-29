package com.example.mystokapp.repository

import com.example.mystokapp.model.Kategori
import com.example.mystokapp.service.KategoriService
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

interface KategoriRepository {
    @OptIn(InternalSerializationApi::class)
    suspend fun insertKategori(kategori: Kategori)

    @OptIn(InternalSerializationApi::class)
    suspend fun getKategori(): List<Kategori>

    @OptIn(InternalSerializationApi::class)
    suspend fun updateKategori(idKategori: Int, kategori: Kategori)

    suspend fun deleteKategori(idKategori: Int)

    @OptIn(InternalSerializationApi::class)
    suspend fun getKategoriById(idKategori: Int): Kategori
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository {
    @OptIn(InternalSerializationApi::class)
    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun updateKategori(idKategori: Int, kategori: Kategori) {
        kategoriApiService.updateKategori(idKategori, kategori)
    }

    override suspend fun deleteKategori(idKategori: Int) {
        try {
            val response = kategoriApiService.deleteKategori(idKategori)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete kategori. HTTP Status Code: " + "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun getKategori(): List<Kategori> = kategoriApiService.getAllKategori()

    @OptIn(InternalSerializationApi::class)
    override suspend fun getKategoriById(idKategori: Int): Kategori {
        return kategoriApiService.getKategoriById(idKategori)
    }
}
