package com.tiago.desafio.ui.dialog.details

import android.view.View
import androidx.lifecycle.ViewModel
import com.tiago.desafio.repository.PokemonRepository

class DetailsPokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    var listener: DetailsPokemonListener? = null

    fun initViewModel() {
        listener!!.getDetails(repository.getClick())
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
        listener!!.share(repository.getClick().url)
    }

}