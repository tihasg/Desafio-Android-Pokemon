package com.tiago.desafio.repository

import com.orhanobut.hawk.Hawk
import com.tiago.desafio.network.ApiService
import com.tiago.desafio.network.response.Pokemon

class PokemonRepository(private val apiService: ApiService) {

    suspend fun getPokeMons(limit: Int, offset: Int) =
        apiService.getPokemonResponse(limit = limit, offset = offset)

    fun saveListPokemon(list: List<Pokemon>) {
        Hawk.put(LISTPOKEMOS, list)
    }

    fun saveClick(click: Pokemon) {
        Hawk.put(CLICKPOKEMON, click)
    }

    fun getClick(): Pokemon {
        return Hawk.get(CLICKPOKEMON)
    }

    fun getListPokemons(): List<Pokemon> {
        return Hawk.get(LISTPOKEMOS)
    }

    companion object {
        const val LISTPOKEMOS = "listPOKE"
        const val CLICKPOKEMON = "newsClick"
    }
}