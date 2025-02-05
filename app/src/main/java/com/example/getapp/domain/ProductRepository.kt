package com.example.getapp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.getapp.data.model.Product
import com.example.getapp.data.apicalls.RetrofitInstance

class ProductRepository {
    private val api = RetrofitInstance.api

    fun getProducts(): Flow<List<Product>> = flow {
        try {
            val response = api.getProducts()
            emit(response.products)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
