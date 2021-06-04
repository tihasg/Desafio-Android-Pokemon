package com.tiago.desafio.ui.dialog.error

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ErrorDialogViewModel : ViewModel() {
    var error = ""
    var listener: ErrorListener? = null

    private val _error = MutableLiveData<String>()
    val errorApi: LiveData<String>
        get() = _error

    fun setTextError(text: String) {
        error = text
        _error.postValue(text)
    }

    fun onClose(view: View) {
        close()
    }

    fun close() {
        listener!!.close()
    }
}