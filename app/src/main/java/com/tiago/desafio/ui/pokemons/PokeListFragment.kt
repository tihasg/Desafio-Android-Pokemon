package com.tiago.desafio.ui.pokemons

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.ViewCompat.canScrollVertically
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiago.desafio.databinding.FragmentPokemonsBinding
import com.tiago.desafio.network.response.Pokemon
import com.tiago.desafio.ui.dialog.details.DetailsPokemonDialog
import com.tiago.desafio.ui.pokemons.adapter.PokeListRecyclerAdapter
import com.tiago.desafio.utils.gone
import com.tiago.desafio.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokeListFragment : Fragment() {

    private val viewModel: PokeListViewModel by viewModel()
    lateinit var binding: FragmentPokemonsBinding
    private var adapterNews = PokeListRecyclerAdapter(this::getClickPokemon)
    private var numPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
    }

    private fun initViews() {
        binding.newsList.layoutManager = LinearLayoutManager(context)
            .also { it.orientation = LinearLayoutManager.VERTICAL }
        binding.newsList.adapter = adapterNews

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapterNews.filter.filter(p0)
                (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    binding.searchView.windowToken,
                    0
                )
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapterNews.filter.filter(query)
                return true
            }
        })

        binding.newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!canScrollVertically(
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
        binding.viewmodel = viewModel
        viewModel.initViewModel()

        viewModel.newsList.observe(requireActivity(), Observer<List<Pokemon>> {
            adapterNews.items = it
            adapterNews.newsList = it
            adapterNews.notifyDataSetChanged()
        })

        viewModel.loading.observe(requireActivity(), Observer<Boolean> {
            when (it) {
                true -> binding.progressBar.visible()
                false -> binding.progressBar.gone()
            }
        })
    }

    private fun getClickPokemon(obj: Pokemon) {
        viewModel.saveClick(obj)
        DetailsPokemonDialog.newInstance()
            .show(requireActivity().supportFragmentManager, DetailsPokemonDialog.TAG)
    }

    companion object {
        fun newInstance() = PokeListFragment()
    }
}