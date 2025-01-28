package com.example.mystokapp.service

import com.example.mystokapp.model.Merk
import kotlinx.serialization.InternalSerializationApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MerkService {

    @OptIn(InternalSerializationApi::class)
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertmerk.php")
    suspend fun insertMerk(@Body merk: Merk)

    @OptIn(InternalSerializationApi::class)
    @GET("readmerk.php")
    suspend fun getAllMerk(): List<Merk>

    @OptIn(InternalSerializationApi::class)
    @GET("detailmerk.php")
    suspend fun getMerkById(@Query("id_merk") idMerk: Int): Merk

    @OptIn(InternalSerializationApi::class)
    @PUT("editmerk.php")
    suspend fun updateMerk(@Query("id_merk") idMerk: Int, @Body merk: Merk)

    @DELETE("deletemerk.php")
    suspend fun deleteMerk(@Query("id_merk") idMerk: Int): Response<Void>
}