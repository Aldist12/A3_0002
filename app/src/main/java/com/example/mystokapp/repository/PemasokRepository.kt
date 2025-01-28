package com.example.mystokapp.repository
import com.example.mystokapp.model.Pemasok
import com.example.mystokapp.service.PemasokService
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

interface PemasokRepository {
    @OptIn(InternalSerializationApi::class)
    suspend fun insertPemasok(pemasok: Pemasok)

    @OptIn(InternalSerializationApi::class)
    suspend fun getPemasok(): List<Pemasok>

    @OptIn(InternalSerializationApi::class)
    suspend fun updatePemasok(idPemasok: Int, pemasok: Pemasok)

    suspend fun deletePemasok(idPemasok: Int)

    @OptIn(InternalSerializationApi::class)
    suspend fun getPemasokById(idPemasok: Int): Pemasok
}

class NetworkPemasokRepository(
    private val pemasokApiService: PemasokService
) : PemasokRepository {
    @OptIn(InternalSerializationApi::class)
    override suspend fun insertPemasok(pemasok: Pemasok) {
        pemasokApiService.insertPemasok(pemasok)
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun updatePemasok(idPemasok: Int, pemasok: Pemasok) {
        pemasokApiService.updatePemasok(idPemasok, pemasok)
    }

    override suspend fun deletePemasok(idPemasok: Int) {
        try {
            val response = pemasokApiService.deletePemasok(idPemasok)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pemasok. HTTP Status Code: " +
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
    override suspend fun getPemasok(): List<Pemasok> =
        pemasokApiService.getAllPemasok()

    @OptIn(InternalSerializationApi::class)
    override suspend fun getPemasokById(idPemasok: Int): Pemasok {
        return pemasokApiService.getPemasokById(idPemasok)
    }
}
