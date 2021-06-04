package com.tiago.desafio.ui.home

interface HomeListener {
    fun showLoading()
    fun hideLoading()
    fun apiSuccess()
    fun apiError(string: String)
}