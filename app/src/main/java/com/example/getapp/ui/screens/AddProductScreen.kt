package com.example.getapp.ui.screens

import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.getapp.data.model.Product
import com.example.getapp.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductViewModel) {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }

    val isFormValid = title.isNotBlank() && price.isNotBlank() && description.isNotBlank()

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        imageUrl = uri?.toString() ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFA31D1D))
            )
        },
        containerColor = Color(0xFFFEF9E1)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", color = Color(0xFF6D2323)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFE5D0AC),
                    focusedIndicatorColor = Color(0xFFA31D1D),
                    cursorColor = Color(0xFFA31D1D)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price", color = Color(0xFF6D2323)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFE5D0AC),
                    focusedIndicatorColor = Color(0xFFA31D1D),
                    cursorColor = Color(0xFFA31D1D)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = Color(0xFF6D2323)) },
                maxLines = 4,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFE5D0AC),
                    focusedIndicatorColor = Color(0xFFA31D1D),
                    cursorColor = Color(0xFFA31D1D)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA31D1D)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pick Image from Gallery", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            selectedImageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = Color(0xFFA31D1D),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Button(
                    onClick = {
                        isLoading = true
                        val newProduct = Product(
                            id = 0,
                            title = title,
                            price = price.toDoubleOrNull() ?: 0.0,
                            description = description,
                            thumbnail = imageUrl,
                            category = "Misc",
                            brand = "Generic",
                            discountPercentage = 0.0,
                            rating = 0.0,
                            stock = 0,
                            images = listOf(imageUrl)
                        )

                        viewModel.postProduct(newProduct) {
                            isLoading = false
                            showSnackbar = true
                            navController.popBackStack()
                        }
                    },
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA31D1D)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Product", color = Color.White)
                }
            }
        }
    }

    if (showSnackbar) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = { showSnackbar = false }) {
                    Text("OK", color = Color.White)
                }
            },
            containerColor = Color(0xFFA31D1D),
            contentColor = Color.White
        ) {
            Text("Product added successfully!")
        }
    }
}
