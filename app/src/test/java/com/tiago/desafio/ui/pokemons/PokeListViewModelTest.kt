package com.tiago.desafio.ui.pokemons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
class PokeListViewModelTest : TestCase() {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokeListViewModel

    @Mock
    private lateinit var repository: PokemonRepository

    @Mock
    private lateinit var newsObserver: Observer<NewsResponse>

    @Mock
    private lateinit var newsListObserver: Observer<List<NewsResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = PokeListViewModel(repository)
    }

    @Test
    fun initViewModel() {
        repository.saveNews(listOf(getMockNewResponse()))
        viewModel.newsList.observeForever(newsListObserver)
        viewModel.initViewModel()
        verify(newsListObserver).onChanged(arrayListOf())
    }

    @Test
    fun saveClick() {
        viewModel.pokeList.observeForever(newsObserver)
        viewModel.saveClick(getMockNewResponse())
        verify(newsObserver).onChanged(getMockNewResponse())
    }

    private fun getMockNewResponse(): NewsResponse {
        return NewsResponse("1", false, "", "", "", "", "", "")
    }
}