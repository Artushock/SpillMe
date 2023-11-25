package com.artushock.apps.spillme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.artushock.apps.spillme.ui.addnewplant.AddNewPlant
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
                AddNewPlant(navController = navController)
            }
        }
        composable("addNewPlantType") {
            AppBarPage(title = "Add plant type", navController = navController) {
                AddNewPlantTypeScreen(navController = navController)
            }
        }
    }
}