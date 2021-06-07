package com.tiago.desafio.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.tiago.desafio.network.response.PokemonResponse
import com.tiago.desafio.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
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
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var repository: PokemonRepository

    @Mock
    private lateinit var pokemonListObserver: Observer<List<Pokemon>>

    @Mock
    private lateinit var listener: HomeListener

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = HomeViewModel(repository)
        viewModel.listener = listener
    }

    @Test
    fun getPokemons() = TestCoroutineDispatcher().runBlockingTest {

        Mockito.`when`(repository.getPokeMons(10, 5)).thenReturn(Response.success(mockListPokemons()))

        viewModel.poke.observeForever(pokemonListObserver)

        viewModel.getListApi()

        verify(pokemonListObserver).onChanged(mockListPokemons().results)
    }

    private fun mockListPokemons(): PokemonResponse {
        return PokemonResponse(arrayListOf())
    }
}
