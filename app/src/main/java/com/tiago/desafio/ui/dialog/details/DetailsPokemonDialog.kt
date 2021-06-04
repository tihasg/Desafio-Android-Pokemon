package com.tiago.desafio.ui.dialog.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.tiago.desafio.R
import com.tiago.desafio.databinding.FragmentDetailsBinding
import com.tiago.desafio.network.response.Pokemon
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsPokemonDialog : DialogFragment(), DetailsPokemonListener {
    lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullscreenDialogMargin)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        viewModel.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.initViewModel()
    }

    companion object {
        const val TAG = "details_fragment"
        fun newInstance() = DetailsPokemonDialog()
    }

    override fun close() {
        dialog?.dismiss()
    }

    override fun openBrowser(url: String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun getDetails(pokemon: Pokemon) {
        binding.textViewTitle.text = pokemon.name
        Glide.with(binding.imageView).load(pokemon.url).into(binding.imageView)
    }
}