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
import com.artushock.apps.spillme.ui.navigation.Routes.ADD_PLANT_ROUTE
import com.artushock.apps.spillme.ui.navigation.Routes.ADD_PLANT_TYPE_ROUTE
import com.artushock.apps.spillme.ui.navigation.Routes.AUTH_ROUTE
import com.artushock.apps.spillme.ui.navigation.Routes.PLANTS_ROUTE
import com.artushock.apps.spillme.ui.navigation.Routes.PLANT_DETAILS_ARG
import com.artushock.apps.spillme.ui.navigation.Routes.PLANT_DETAILS_ROUTE
import com.artushock.apps.spillme.ui.plants.details.PlantDetails
import com.artushock.apps.spillme.ui.plants.main.MainListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AUTH_ROUTE) {

        composable(AUTH_ROUTE)
        {
            AuthScreen(navController)
        }

        composable(PLANTS_ROUTE)
        {
            AppBarPage(
                title = "Plants",
                navController = navController,
                navBackEnabled = false,
                plusAction = { navController.navigate(ADD_PLANT_ROUTE) }) {
                MainListScreen(navController = navController)
            }
        }
        composable(ADD_PLANT_ROUTE) {
            AppBarPage(title = "Add new plant", navController = navController) {
                AddNewPlantScreen(navController = navController)
            }
        }
        composable(route = ADD_PLANT_TYPE_ROUTE) {
            AppBarPage(title = "Add plant type", navController = navController) {
                AddNewPlantTypeScreen(
                    navController = navController
                )
            }
        }
        composable(
            route = PLANT_DETAILS_ROUTE,
            arguments = listOf(navArgument(PLANT_DETAILS_ARG) { type = NavType.IntType })
        ) { navBackStackEntry ->
            val plantId = navBackStackEntry.arguments?.getInt(PLANT_DETAILS_ARG)
            AppBarPage(title = "Details", navController = navController) {
                PlantDetails(plantId)
            }
        }
    }
}

object Routes {
    /**
     * SpillMe navigation: Auth screen route
     * */
    val AUTH_ROUTE = "auth"

    /**
     * SpillMe navigation: Main plants list screen route
     * */
    val PLANTS_ROUTE = "plants"

    /**
     * SpillMe navigation: Plant id for navigation to plant details screen using  PLANT_DETAILS_ROUTE
     * */
    val PLANT_DETAILS_ARG = "plantId"

    /**
     * SpillMe navigation: Plaint details screen route
     * */
    val PLANT_DETAILS_ROUTE = "plants/{$PLANT_DETAILS_ARG}"

    /**
     * SpillMe navigation: Add new plant screen route
     * */
    val ADD_PLANT_ROUTE = "add_plant"

    /**
     * SpillMe navigation: Add new plant type screen route
     * */
    val ADD_PLANT_TYPE_ROUTE = "add_plant_type"

    fun getPlantDetailsRoute(plantId: Int) = "plants/$plantId"
}

