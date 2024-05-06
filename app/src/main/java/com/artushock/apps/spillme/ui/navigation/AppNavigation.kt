package com.artushock.apps.spillme.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.artushock.apps.spillme.ui.addplant.plant.AddNewPlantScreen
import com.artushock.apps.spillme.ui.addplant.type.AddPlantTypeContainer
import com.artushock.apps.spillme.ui.auth.AuthScreen
import com.artushock.apps.spillme.ui.base.AppBarPage
import com.artushock.apps.spillme.ui.mainlist.MainListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NAV_AUTH_SCREEN) {

        composable(NAV_AUTH_SCREEN)
        {
            AuthScreen(navController)
        }

        composable(NAV_MAIN_LIST)
        {
            AppBarPage(title = "Plants", navController = navController, actions = true) {
                MainListScreen(navController = navController)
            }
        }
        composable(NAV_ADD_NEW_PLANT) {
            AppBarPage(title = "Add new plant", navController = navController) {
                AddNewPlantScreen(navController = navController)
            }
        }
        composable(NAV_ADD_NEW_PLANT_TYPE) {
            AppBarPage(title = "Add new plant type", navController = navController) {
                AddPlantTypeContainer(navController = navController)
            }
        }
    }
}

const val NAV_AUTH_SCREEN = "NAV_AUTH_SCREEN"
const val NAV_MAIN_LIST = "NAV_MAIN_LIST"
const val NAV_ADD_NEW_PLANT = "NAV_ADD_NEW_PLANT"
const val NAV_ADD_NEW_PLANT_TYPE = "NAV_ADD_NEW_PLANT_TYPE"