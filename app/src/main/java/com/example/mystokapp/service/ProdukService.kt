package com.example.mystokapp.service

import com.example.mystokapp.model.Produk
import kotlinx.serialization.InternalSerializationApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface ProdukService {

    @OptIn(InternalSerializationApi::class)
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertproduk.php")
    suspend fun insertProduk(@Body produk: Produk)

    @OptIn(InternalSerializationApi::class)
    @GET("readproduk.php")
    suspend fun getAllProduk(): List<Produk>

    @OptIn(InternalSerializationApi::class)
    @GET("detailproduk.php/{id_produk}")
    suspend fun getProdukById(@Query("id_produk") idProduk: Int): Produk

    @OptIn(InternalSerializationApi::class)
    @PUT("editproduk.php/{id_produk}")
    suspend fun updateProduk(@Query("id_produk") idProduk: Int, @Body produk: Produk)

    @DELETE("deleteproduk.php/{id_produk}")
    suspend fun deleteProduk(@Query("id_produk") idProduk: Int): Response<Void>
}
