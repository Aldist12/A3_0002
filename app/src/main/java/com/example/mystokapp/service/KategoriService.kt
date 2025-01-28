package com.example.mystokapp.service


import com.example.mystokapp.model.Kategori
import kotlinx.serialization.InternalSerializationApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KategoriService {

    @OptIn(InternalSerializationApi::class)
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertkategori.php")
    suspend fun insertKategori(@Body kategori: Kategori)

    @OptIn(InternalSerializationApi::class)
    @GET("readkategori.php")
    suspend fun getAllKategori(): List<Kategori>

    @OptIn(InternalSerializationApi::class)
    @GET("detailkategori.php")
    suspend fun getKategoriById(@Query("id_kategori") idKategori: Int): Kategori

    @OptIn(InternalSerializationApi::class)
    @PUT("editkategori.php")
    suspend fun updateKategori(@Query("id_kategori") idKategori: Int, @Body kategori: Kategori)

    @DELETE("deletekategori.php")
    suspend fun deleteKategori(@Query("id_kategori") idKategori: Int): Response<Void>
}