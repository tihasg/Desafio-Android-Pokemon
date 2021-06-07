package com.tiago.desafio.network.response

import com.google.gson.annotations.SerializedName
import com.tiago.desafio.model.Ability
import com.tiago.desafio.model.Move
import com.tiago.desafio.model.Sprite

class PokemonDetailResponse  {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("sprites")
    var sprites: Sprite? = null

    @SerializedName("moves")
    var moves: ArrayList<Move>? = null

    @SerializedName("weight")
    var weight: Int? = null

    @SerializedName("height")
    var height: Int? = null

    @SerializedName("abilities")
    var abilities: ArrayList<Ability>? = null
}