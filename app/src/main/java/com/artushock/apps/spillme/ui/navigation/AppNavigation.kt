package com.artushock.apps.spillme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.artushock.apps.spillme.ui.addnewplant.AddNewPlantScreen
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.AddNewPlantTypeScreen
import com.artushock.apps.spillme.ui.base.AppBarPage
import com.artushock.apps.spillme.ui.mainlist.MainListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainListScreen") {
        composable("mainListScreen")
        {
            AppBarPage(title = "Plants", navController = navController, actions = true) {
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
        composable("frequencyOfCare",
            arguments = listOf(
                navArgument("plant_type_name") {
                    NavType.StringType
                },
                navArgument("plantType_description") {
                    NavType.StringType
                }
            )) {
            AppBarPage(title = "Frequency of care", navController = navController) {
//                FrequencyOfCareScreen(navController = navController)
            }
        }
    }
}