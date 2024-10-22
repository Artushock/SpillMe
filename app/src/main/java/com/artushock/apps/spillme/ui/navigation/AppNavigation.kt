package com.artushock.apps.spillme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.artushock.apps.spillme.ui.addnewplant.AddNewPlantScreen
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.AddNewPlantTypeScreen
import com.artushock.apps.spillme.ui.auth.AuthScreen
import com.artushock.apps.spillme.ui.base.AppBarPage
import com.artushock.apps.spillme.ui.plants.details.PlantDetails
import com.artushock.apps.spillme.ui.plants.main.MainListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "authScreen") {

        composable("authScreen")
        {
            AuthScreen(navController)
        }

        composable("mainListScreen")
        {
            AppBarPage(
                title = "Plants",
                navController = navController,
                navBackEnabled = false,
                plusAction = { navController.navigate("addNewPlant") }) {
                MainListScreen(navController = navController)
            }
        }
        composable("addNewPlant") {
            AppBarPage(title = "Add new plant", navController = navController) {
                AddNewPlantScreen(navController = navController)
            }
        }
        composable(
            route = "addNewPlantType",

            ) { navBackStackEntry ->
            AppBarPage(title = "Add plant type", navController = navController) {
                AddNewPlantTypeScreen(
                    navController = navController
                )
            }
        }
        composable(
            route = "plants/{plantId}",
            arguments = listOf(navArgument("plantId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val plantId = navBackStackEntry.arguments?.getInt("plantId")

            AppBarPage(title = "Details", navController = navController) {
                PlantDetails(plantId)
            }
        }
    }
}