package com.tiago.desafio.ui.dialog.details

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiago.desafio.network.response.PokemonDetailResponse
import com.tiago.desafio.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailsPokemonViewModel(private val repository: PokemonRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val _pokemon = MutableLiveData<PokemonDetailResponse>()
    val pokemon: LiveData<PokemonDetailResponse>
        get() = _pokemon

    var listener: DetailsPokemonListener? = null

    fun initViewModel() {
        getDetailsApi(repository.getClick().name ?: "")
    }

     fun getDetailsApi(name: String) {
        launch {
            try {
                val response = repository.getPokeDetails(name)
                if (response.isSuccessful) {
                    _pokemon.postValue(response.body())
                    repository.savePokemon(response.body()!!)
                }
            } catch (e: Exception) {
                _pokemon.postValue(null)
            }
        }
    }

    fun onClose(view: View) {
        close()
    }

    fun openBrowser(view: View) {
        browser()
    }

    fun close() {
        listener!!.close()
    }

    private fun browser() {
        val pokemon = repository.getPokemon().name + " habilidade principal: " + repository.getPokemon().abilities?.get(0)?.ability?.name
        listener!!.share(pokemon)
    }

}