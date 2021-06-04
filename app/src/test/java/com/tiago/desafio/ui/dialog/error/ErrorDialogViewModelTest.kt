package com.tiago.desafio.ui.dialog.error

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
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
class ErrorDialogViewModelTest : TestCase() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ErrorDialogViewModel

    @Mock
    private lateinit var listener: ErrorListener

    @Mock
    private lateinit var errorApiObserver: Observer<String>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ErrorDialogViewModel()
        viewModel.listener = listener
    }

    @Test
    fun testSetError() {
        val error = "Erro ao carregar Lista"
        viewModel.errorApi.observeForever(errorApiObserver)

        viewModel.setTextError(error)

        verify(errorApiObserver).onChanged(error)
    }

    @Test
    fun testOnClose() {
        viewModel.close()
        verify(listener).close()
    }
}