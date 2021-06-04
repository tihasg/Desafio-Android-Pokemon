package com.tiago.desafio.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tiago.desafio.R
import com.tiago.desafio.databinding.ActivityHomeBinding
import com.tiago.desafio.ui.dialog.error.ErrorDialog
import com.tiago.desafio.ui.pokemons.PokeListFragment
import com.tiago.desafio.utils.gone
import com.tiago.desafio.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), HomeListener {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()
    }

    private fun initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewmodel = viewModel
        viewModel.listener = this
        viewModel.getPokemons()
    }

    override fun showLoading() {
        binding.progressBar2.visible()
    }

    override fun hideLoading() {
        binding.progressBar2.gone()
    }

    override fun apiSuccess() {
        val frameLayout = PokeListFragment.newInstance()
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container, frameLayout)
        transition.commit()
    }

    override fun apiError(string: String) {
        ErrorDialog.newInstance(string).show(supportFragmentManager, ErrorDialog.TAG)
    }
}