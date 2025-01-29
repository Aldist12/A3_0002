package com.example.mystokapp.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class Pemasok(
    @SerialName("id_pemasok")
    val idPemasok: Int = 0,

    @SerialName("nama_pemasok")
    val namaPemasok: String,

    @SerialName("alamat_pemasok")
    val alamatPemasok: String,

    @SerialName("telepon_pemasok")
    val teleponPemasok: String
)
