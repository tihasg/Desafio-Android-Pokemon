package com.tiago.desafio.ui.dialog.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.tiago.desafio.R
import com.tiago.desafio.databinding.FragmentErrorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ErrorDialog : DialogFragment(), ErrorListener {
    lateinit var binding: FragmentErrorBinding
    private val viewModel: ErrorDialogViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullscreenDialogMargin)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorBinding.inflate(inflater, container, false)
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
        viewModel.setTextError(textError)
    }


    companion object {
        const val TAG = "error_fragment"
        var textError = ""
        fun newInstance(string: String): ErrorDialog {
            textError = string
            return ErrorDialog()
        }
    }

    override fun close() {
        dialog?.dismiss()
    }
}