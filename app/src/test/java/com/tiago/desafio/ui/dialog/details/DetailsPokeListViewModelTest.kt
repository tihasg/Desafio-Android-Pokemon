package com.tiago.desafio.ui.dialog.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.tiago.desafio.repository.PokemonRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DetailsPokeListViewModelTest : TestCase() {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsPokemonViewModel

    @Mock
    private lateinit var repository: PokemonRepository

    @Mock
    private lateinit var listener: DetailsPokemonListener


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
    fun initViewModel() {
        repository.saveClick(getMock())
        viewModel.initViewModel()
        verify(listener).getDetails(repository.getClick())
    }

    private fun getMock(): Pokemon {
        return Pokemon(name= "Pikachu", url = "")
    }
}