package com.artushock.apps.spillme.ui.mainlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.artushock.apps.spillme.Plant
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.dev.PlantsProvider
import com.artushock.apps.spillme.ui.theme.MainBeige
import com.artushock.apps.spillme.ui.theme.MainBrown

@Composable
fun MainListScreen(navController: NavHostController) {
    val plants = PlantsProvider().getPlants()

    Box {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = plants,
                itemContent = { PlantItem(plant = it) }
            )
        }
    }
}

@Composable
fun PlantItem(plant: Plant) {
    Column(
        Modifier
            .shadow(3.dp, shape = RoundedCornerShape(8.dp))
            .clip(shape = RoundedCornerShape(6.dp))
            .fillMaxSize()
            .background(MainBeige)

    ) {
        Text(
            text = plant.name,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            style = TextStyle(color = MainBrown, fontSize = 16.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(2.dp)
                .background(MainBrown)
        )
        AsyncImage(
            model = plant.photo,
            contentDescription = "Plant photo",
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(2.dp)
                .background(MainBrown)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MainBeige)
                .padding(8.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,


            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_watering),
                    contentDescription = null
                )
                Text(
                    text = "12 days",
                    modifier = Modifier
                        .padding(8.dp),
                    style = TextStyle(color = MainBrown, fontSize = 11.sp),
                    textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_feeding),
                    contentDescription = null
                )
                Text(
                    text = "12 days",
                    modifier = Modifier
                        .padding(8.dp),
                    style = TextStyle(color = MainBrown, fontSize = 11.sp),
                    textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_spray),
                    contentDescription = null
                )
                Text(
                    text = "12 days",
                    modifier = Modifier
                        .padding(8.dp),
                    style = TextStyle(color = MainBrown, fontSize = 11.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
