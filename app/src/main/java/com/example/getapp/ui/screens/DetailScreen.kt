package com.example.getapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.getapp.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(productId: Int, viewModel: ProductViewModel, navController: NavController) {
    val products by viewModel.products.collectAsState()
    val product = products.find { it.id == productId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.title ?: "Product Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFA31D1D))
            )
        },
        containerColor = Color(0xFFFEF9E1) // Background color
    ) { padding ->
        product?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE5D0AC))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = it.thumbnail,
                            contentDescription = it.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Price: $${it.price}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFA31D1D)
                        )

                        Text(
                            text = "Category: ${it.category}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6D2323)
                        )

                        Text(
                            text = "Brand: ${it.brand}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6D2323)
                        )

                        Text(
                            text = "Stock: ${it.stock}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6D2323)
                        )

                        Text(
                            text = "Rating: ${it.rating} ★",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFA31D1D)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Description:",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFA31D1D)
                        )

                        Text(
                            text = it.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Product not found!",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFA31D1D)
            )
        }
    }
}

