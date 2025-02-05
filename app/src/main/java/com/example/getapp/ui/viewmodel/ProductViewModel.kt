package com.example.getapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.getapp.data.apicalls.ProductApi
import com.example.getapp.data.model.Product

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val api = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApi::class.java)

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = api.getProducts()
                _products.value = response.products  // Extract the list from API response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun postProduct(product: Product, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = api.addProduct(product)
                _products.value += response
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


