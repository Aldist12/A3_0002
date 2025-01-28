package com.example.mystokapp.repository
import com.example.mystokapp.model.Merk
import com.example.mystokapp.service.MerkService
import kotlinx.serialization.InternalSerializationApi
import okio.IOException

interface MerkRepository {
    @OptIn(InternalSerializationApi::class)
    suspend fun insertMerk(merk: Merk)

    @OptIn(InternalSerializationApi::class)
    suspend fun getMerk(): List<Merk>

    @OptIn(InternalSerializationApi::class)
    suspend fun updateMerk(idMerk: Int, merk: Merk)

    suspend fun deleteMerk(idMerk: Int)

    @OptIn(InternalSerializationApi::class)
    suspend fun getMerkById(idMerk: Int): Merk
}

class NetworkMerkRepository(
    private val merkApiService: MerkService
) : MerkRepository {
    @OptIn(InternalSerializationApi::class)
    override suspend fun insertMerk(merk: Merk) {
        merkApiService.insertMerk(merk)
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun updateMerk(idMerk: Int, merk: Merk) {
        merkApiService.updateMerk(idMerk, merk)
    }

    override suspend fun deleteMerk(idMerk: Int) {
        try {
            val response = merkApiService.deleteMerk(idMerk)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete merk. HTTP Status Code: " +
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
    override suspend fun getMerk(): List<Merk> =
        merkApiService.getAllMerk()

    @OptIn(InternalSerializationApi::class)
    override suspend fun getMerkById(idMerk: Int): Merk {
        return merkApiService.getMerkById(idMerk)
    }
}
