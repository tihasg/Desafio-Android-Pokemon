package com.tiago.desafio.network

import com.tiago.desafio.network.response.PokemonResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonResponse(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<Response<PokemonResponse>>
}