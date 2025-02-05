package com.example.getapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.getapp.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(productId: Int, viewModel: ProductViewModel) {
    val products by viewModel.products.collectAsState()
    val product = products.find { it.id == productId }

    product?.let {
        Scaffold(
            topBar = { TopAppBar(title = { Text(it.title) }) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = it.thumbnail,
                    contentDescription = it.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Text(text = "Price: $${it.price}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Category: ${it.category}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Brand: ${it.brand}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Stock: ${it.stock}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Rating: ${it.rating}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Description:", style = MaterialTheme.typography.titleMedium)
                Text(text = it.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    } ?: Text("Product not found!")
}
