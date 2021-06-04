package com.tiago.desafio.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tiago.desafio.network.ApiService
import com.tiago.desafio.repository.PokemonRepository
import com.tiago.desafio.ui.dialog.details.DetailsPokemonViewModel
import com.tiago.desafio.ui.dialog.error.ErrorDialogViewModel
import com.tiago.desafio.ui.home.HomeViewModel
import com.tiago.desafio.ui.pokemons.PokeListViewModel
import com.tiago.desafio.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttp(get()) }
    single { provideRetrofit(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { PokeListViewModel(get()) }
    viewModel { ErrorDialogViewModel() }
    viewModel { DetailsPokemonViewModel(get()) }
}

val repositoryModule = module {
    factory { PokemonRepository(get()) }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

private fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.apply {
        addInterceptor(httpLoggingInterceptor)
    }
    return okHttpClient.build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
    val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    return retrofit.create(ApiService::class.java)
}