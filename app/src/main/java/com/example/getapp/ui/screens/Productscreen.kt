package com.example.getapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.getapp.ui.sections.ProductItem
import com.example.getapp.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController, viewModel: ProductViewModel) {
    val products by viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFE5D0AC)
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addProduct") },
                containerColor = Color(0xFFA31D1D)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Product", tint = Color(0xFFFEF9E1))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFEF9E1))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            val chunkedProducts = products.chunked(2)

            for (rowProducts in chunkedProducts) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (product in rowProducts) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                        ) {
                            ProductItem(product) {
                                navController.navigate("detailScreen/${product.id}")
                            }
                        }
                    }
                    if (rowProducts.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
