package com.tiago.desafio.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiago.desafio.network.response.Pokemon
import com.tiago.desafio.repository.PokemonRepository
import com.tiago.desafio.utils.Constants.OFFSET
import com.tiago.desafio.utils.Constants.PAGE_LIMIT
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


    fun getListApi() {
        launch {
            try {
                val response = repository.getPokeMons(PAGE_LIMIT, OFFSET)
                if (response.isSuccessful) {
                    repository.saveListPokemon(response.body()!!.results)
                    _pokeList.postValue(response.body()!!.results)

                } else {
                    _pokeList.postValue(arrayListOf())
                    listener.apiError(
                        "Erro ao carregar lista de api"
                    )
                }
            } catch (e: Exception) {
                _pokeList.postValue(arrayListOf())

                listener.apiError(
                    e.message
                        ?: "Erro ao carregar lista de api"
                )
            }
        }
    }

    fun getNextPage(num: Int) {
        launch {
            try {
                val response = repository.getPokeMons(PAGE_LIMIT, num)
                if (response.isSuccessful) {
                    val listApi = response.body()!!.results
                    val listCache = repository.getListPokemons()
                    val listActual = arrayListOf<Pokemon>()
                    listActual.addAll(listCache)
                    listActual.addAll(listApi)
                    _pokeList.postValue(listActual)
                }
            } catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun saveClick(newsResponse: Pokemon) {
        repository.saveClick(newsResponse)
    }


}