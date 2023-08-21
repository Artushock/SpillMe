package com.artushock.apps.spillme.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.artushock.apps.spillme.Plant
import com.artushock.apps.spillme.dev.PlantsProvider
import com.artushock.apps.spillme.ui.theme.MainBeige
import com.artushock.apps.spillme.ui.theme.MainBrown
import com.artushock.apps.spillme.ui.theme.MainGreen
import com.artushock.apps.spillme.ui.theme.PurpleGrey40

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainListScreen") {
        composable("mainListScreen") { MainListScreen(navController) }
    }
}

@Composable
fun MainListScreen(navController: NavHostController) {
    val plants = PlantsProvider().getPlants()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = plants,
            itemContent = { PlantItem(plant = it) }
        )
    }
}

@Composable
fun PlantItem(plant: Plant) {
    Column(
        Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .fillMaxSize()
            .background(MainBeige)

    ) {
        Text(
            text = plant.name,
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            style = TextStyle(color = MainBrown, fontSize = 18.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        AsyncImage(
            model = plant.photo,
            contentDescription = "Plant photo",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun Foo() {
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(MainGreen)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "plant.name",
            modifier = Modifier.padding(8.dp),
            style = TextStyle(color = PurpleGrey40, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
        AsyncImage(
            model = "plant.photo",
            contentDescription = "Plant photo",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}