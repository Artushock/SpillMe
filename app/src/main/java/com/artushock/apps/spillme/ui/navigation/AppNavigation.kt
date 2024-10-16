package com.artushock.apps.spillme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.artushock.apps.spillme.ui.addnewplant.AddNewPlantScreen
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.AddNewPlantTypeScreen
import com.artushock.apps.spillme.ui.auth.AuthScreen
import com.artushock.apps.spillme.ui.base.AppBarPage
import com.artushock.apps.spillme.ui.mainlist.MainListScreen

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
    }
}