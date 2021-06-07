package com.tiago.desafio.ui.dialog.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.tiago.desafio.network.response.PokemonDetailResponse
import com.tiago.desafio.repository.PokemonRepository
import junit.framework.TestCase
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner.Silent::class)
class DetailsPokeListViewModelTest : TestCase() {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsPokemonViewModel

    @Mock
    private lateinit var repository: PokemonRepository

    @Mock
    private lateinit var listener: DetailsPokemonListener

    @Mock
    private lateinit var pokemonObserver: Observer<PokemonDetailResponse>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailsPokemonViewModel(repository)
        viewModel.listener = listener
    }

    @Test
    fun close() {
        viewModel.close()
        verify(listener).close()
    }

    @Test
    fun getPokemons() = TestCoroutineDispatcher().runBlockingTest {

        Mockito.`when`(repository.getPokeDetails("PIKACHU")).thenReturn(Response.error(400, ResponseBody.create(null, "")))

        viewModel.pokemon.observeForever(pokemonObserver)
        viewModel.getDetailsApi("PIKACHU")

        verify(pokemonObserver).onChanged(PokemonDetailResponse())
    }

}