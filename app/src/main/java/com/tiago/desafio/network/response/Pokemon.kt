package com.tiago.desafio.network.response

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {
    val number
        get():Int {
            val data = url.split("/")
            return data[data.size - 2].toInt()
        }
}