package com.tiago.desafio.ui.home

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiago.desafio.R
import com.tiago.desafio.databinding.ActivityHomeBinding
import com.tiago.desafio.network.response.Pokemon
import com.tiago.desafio.ui.dialog.details.DetailsPokemonDialog
import com.tiago.desafio.ui.dialog.error.ErrorDialog
import com.tiago.desafio.ui.home.adapter.PokeListRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), HomeListener {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    private var adapterNews = PokeListRecyclerAdapter(this::getClickPokemon)
    private var numPage = 0

    lateinit var listPokemonRV : RecyclerView
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        listPokemonRV = findViewById(R.id.listPokemons)
        searchView = findViewById(R.id.searchView)
        listPokemonRV.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.VERTICAL }
        listPokemonRV.adapter = adapterNews

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapterNews.filter.filter(p0)
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                   searchView.windowToken,
                    0
                )
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapterNews.filter.filter(query)
                return true
            }
        })

        listPokemonRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!ViewCompat.canScrollVertically(
                        recyclerView,
                        1
                    ) && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    numPage +=15
                    viewModel.getNewsPage(numPage)
                }
            }
        })

    }

    private fun initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewmodel = viewModel
        viewModel.listener = this
        viewModel.getListApi()

        viewModel.poke.observeForever {
            adapterNews.items = it
            adapterNews.newsList = it
        }
    }


    private fun getClickPokemon(obj: Pokemon) {
        viewModel.saveClick(obj)
        DetailsPokemonDialog.newInstance().show(supportFragmentManager, DetailsPokemonDialog.TAG)
    }

    override fun apiError(string: String) {
        ErrorDialog.newInstance(string).show(supportFragmentManager, ErrorDialog.TAG)
    }
}