package com.tiago.desafio.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiago.desafio.network.response.Pokemon
import com.tiago.desafio.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: PokemonRepository) : ViewModel(), CoroutineScope {
    lateinit var listener: HomeListener

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val _pokeList = MutableLiveData<List<Pokemon>>()
    val poke: LiveData<List<Pokemon>>
        get() = _pokeList

    fun getPokemons() {
        listener.showLoading()
        launch {
            try {
                val response = repository.getPokeMons(10, 0)
                if (response.isSuccessful) {
                    repository.saveListPokemon(response.body()!!.results)
                    _pokeList.postValue(response.body()!!.results)
                    listener.hideLoading()
                } else {
                    _pokeList.postValue(arrayListOf())
                    listener.hideLoading()
                    listener.apiError(
                        "Erro ao carregar lista de api"
                    )
                }
            } catch (e: Exception) {
                _pokeList.postValue(arrayListOf())
                listener.hideLoading()
                listener.apiError(
                    e.message
                        ?: "Erro ao carregar lista de api"
                )
            }
        }
    }
}