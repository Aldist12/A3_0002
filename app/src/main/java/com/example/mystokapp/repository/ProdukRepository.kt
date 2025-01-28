package com.example.mystokapp.repository

import com.example.mystokapp.model.Produk
import com.example.mystokapp.service.ProdukService
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

interface ProdukRepository {
    @OptIn(InternalSerializationApi::class)
    suspend fun insertProduk(produk: Produk)

    @OptIn(InternalSerializationApi::class)
    suspend fun getProduk(): List<Produk>

    @OptIn(InternalSerializationApi::class)
    suspend fun updateProduk(idProduk: Int, produk: Produk)

    suspend fun deleteProduk(idProduk: Int)

    @OptIn(InternalSerializationApi::class)
    suspend fun getProdukById(idProduk: Int): Produk
}

class NetworkProdukRepository(
    private val produkApiService: ProdukService
) : ProdukRepository {
    @OptIn(InternalSerializationApi::class)
    override suspend fun insertProduk(produk: Produk) {
        produkApiService.insertProduk(produk)
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun updateProduk(idProduk: Int, produk: Produk) {
        produkApiService.updateProduk(idProduk, produk)
    }

    override suspend fun deleteProduk(idProduk: Int) {
        try {
            val response = produkApiService.deleteProduk(idProduk)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete produk. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun getProduk(): List<Produk> =
        produkApiService.getAllProduk()

    @OptIn(InternalSerializationApi::class)
    override suspend fun getProdukById(idProduk: Int): Produk {
        return produkApiService.getProdukById(idProduk)
    }
}