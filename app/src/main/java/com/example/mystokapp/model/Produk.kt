package com.example.mystokapp.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class Produk(
    @SerialName("id_produk")
    val idProduk: Int = 0,

    @SerialName("nama_produk")
    val namaProduk: String,

    @SerialName("deskripsi_produk")
    val deskripsiProduk: String,

    val harga: String,

    val stok: String,

    @SerialName("id_kategori")
    val idKategori: String,

    @SerialName("id_pemasok")
    val idPemasok: String,

    @SerialName("id_merk")
    val idMerk: String
)
