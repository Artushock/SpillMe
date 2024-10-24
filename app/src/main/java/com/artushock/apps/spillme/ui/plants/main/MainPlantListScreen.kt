package com.artushock.apps.spillme.ui.plants.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.navigation.Routes
import com.artushock.apps.spillme.ui.plants.main.models.MainListPlantModel
import com.artushock.apps.spillme.ui.theme.MainBeige
import com.artushock.apps.spillme.ui.theme.MainBrown

@Composable
fun MainListScreen(
    navController: NavHostController,
) {
    val viewModel: MainPlantListViewModel = hiltViewModel()
    val plants by viewModel.plants.collectAsState()

    var isBackWasClicked by remember { mutableStateOf(false) }

    BackHandler(
        enabled = !isBackWasClicked
    ) {
        isBackWasClicked = true
    }

    if (isBackWasClicked) {
        ExitDialog(
            onDismiss = { isBackWasClicked = false },
            onConfirmExit = {
                isBackWasClicked = false
                viewModel.clearAuthData()
                navController.popBackStack()
            },
        )
    }

    Box {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = plants,
                itemContent = {
                    PlantItem(plant = it) { plantId ->
                        navController.navigate(Routes.getPlantDetailsRoute(plantId))
                    }
                }
            )
        }
    }
}

@Composable
fun PlantItem(
    plant: MainListPlantModel,
    onItemClicked: (Int) -> Unit,
) {
    Column(
        Modifier
            .shadow(3.dp, shape = RoundedCornerShape(8.dp))
            .clip(shape = RoundedCornerShape(6.dp))
            .fillMaxSize()
            .background(MainBeige)
            .clickable { onItemClicked(plant.localId) }

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
        Image(
            painter = rememberAsyncImagePainter(plant.photo?.toUri()),
            contentDescription = "Plant photo",
            modifier = Modifier.size(180.dp),
            contentScale = ContentScale.Crop
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
                    text = "${plant.nextWatering ?: "-"}  days",
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
                    text = "${plant.nextFeeding ?: "-"} days",
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
                    text = "${plant.nextSpraying ?: "-"}  days",
                    modifier = Modifier
                        .padding(8.dp),
                    style = TextStyle(color = MainBrown, fontSize = 11.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ExitDialog(
    onDismiss: () -> Unit,
    onConfirmExit: () -> Unit,
) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors =
            CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Do you want exit?",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = onDismiss) {
                        Text(text = "No", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = onConfirmExit) {
                        Text(text = "Yes", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}
