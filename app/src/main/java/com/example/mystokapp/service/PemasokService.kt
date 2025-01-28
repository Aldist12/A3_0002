package com.example.mystokapp.service

import com.example.mystokapp.model.Pemasok
import kotlinx.serialization.InternalSerializationApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PemasokService {

    @OptIn(InternalSerializationApi::class)
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertpemasok.php")
    suspend fun insertPemasok(@Body pemasok: Pemasok)

    @OptIn(InternalSerializationApi::class)
    @GET("readpemasok.php")
    suspend fun getAllPemasok(): List<Pemasok>

    @OptIn(InternalSerializationApi::class)
    @GET("detailpemasok.php")
    suspend fun getPemasokById(@Query("id_pemasok") idPemasok: Int): Pemasok

    @OptIn(InternalSerializationApi::class)
    @PUT("editpemasok.php")
    suspend fun updatePemasok(@Query("id_pemasok") idPemasok: Int, @Body pemasok: Pemasok)

    @DELETE("deletepemasok.php")
    suspend fun deletePemasok(@Query("id_pemasok") idPemasok: Int): Response<Void>
}
