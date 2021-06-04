package com.tiago.desafio.ui.pokemons

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

class PokeListViewModel(private val repository: PokemonRepository) : ViewModel(), CoroutineScope {
    private val _pokeList = MutableLiveData<Pokemon>()
    val pokeList: LiveData<Pokemon>
        get() = _pokeList

    private val _newsList = MutableLiveData<List<Pokemon>>()
    val newsList: LiveData<List<Pokemon>>
        get() = _newsList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun initViewModel() {
        _newsList.postValue(repository.getListPokemons())
    }

    fun getNewsPage(num: Int) {
        launch {
            _loading.postValue(true)
            try {
                val response = repository.getPokeMons(10)
                if (response.isSuccessful) {
                    _loading.postValue(false)
                    val listApi = response.body()!!.results

                    val listCache = repository.getListPokemons()

                    val listActual = arrayListOf<Pokemon>()
                    listActual.addAll(listCache)
                    listActual.addAll(listApi)

                    _newsList.postValue(listActual)
                } else {
                    _loading.postValue(false)
                }
            } catch (e: Exception) {
                _loading.postValue(false)
            }
        }
    }

    fun saveClick(newsResponse:Pokemon) {
        _pokeList.postValue(newsResponse)
        repository.saveClick(newsResponse)
    }
}