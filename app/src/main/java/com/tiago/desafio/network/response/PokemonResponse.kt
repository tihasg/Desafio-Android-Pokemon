package com.tiago.desafio.network.response

import com.google.gson.annotations.SerializedName
import com.tiago.desafio.model.Pokemon

data class PokemonResponse(
    @SerializedName("results") val results: List<Pokemon>
)