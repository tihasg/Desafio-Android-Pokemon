package com.tiago.desafio.repository

import com.orhanobut.hawk.Hawk
import com.tiago.desafio.model.Pokemon
import com.tiago.desafio.network.ApiService
import com.tiago.desafio.network.response.PokemonDetailResponse

class PokemonRepository(private val apiService: ApiService) {

    suspend fun getPokeMons(limit: Int, offset: Int) =
        apiService.getPokemonResponse(limit = limit, offset = offset)

    suspend fun getPokeDetails(name: String) =
        apiService.getPokemonDetail(name)


    fun saveListPokemon(list: List<Pokemon>) {
        Hawk.put(LISTPOKEMOS, list)
    }

    fun savePokemon(pokemon: PokemonDetailResponse) {
        Hawk.put(POKEMON, pokemon)
    }

    fun getPokemon(): PokemonDetailResponse {
        return Hawk.get(POKEMON)
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
        const val POKEMON = "pokemon"
        const val CLICKPOKEMON = "newsClick"
    }
}