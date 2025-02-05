import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getapp.ui.screens.AddProductScreen
import com.example.getapp.ui.screens.DetailScreen
import com.example.getapp.ui.screens.ProductScreen
import com.example.getapp.ui.viewmodel.ProductViewModel

@Composable
fun AppNavigation(viewModel: ProductViewModel = ProductViewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "productList") {
        composable("productList") {
            ProductScreen(navController, viewModel)
        }
        composable(
            "detailScreen/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            DetailScreen(productId, viewModel)
        }
        composable("addProduct") {
            AddProductScreen(navController, viewModel)
        }
    }
}
