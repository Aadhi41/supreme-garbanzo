package com.example.getapp.data.apicalls

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.getapp.data.model.Product
import com.example.getapp.data.model.ProductResponse

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductResponse
    @POST("products/add")
    suspend fun addProduct(@Body product: Product): Product
}
