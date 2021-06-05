package com.tiago.desafio.ui.dialog.details

import com.tiago.desafio.network.response.Pokemon

interface DetailsPokemonListener {
    fun close()
    fun share(url: String?)
    fun getDetails(pokemon: Pokemon)
}