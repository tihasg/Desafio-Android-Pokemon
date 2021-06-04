package com.tiago.desafio.network.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("results") val results: List<Pokemon>
)