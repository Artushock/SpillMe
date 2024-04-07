package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.ui.base.colors.getButtonColors

import com.artushock.apps.spillme.ui.base.edittext.EditTextField

@Composable
fun AddNewPlantTypeScreen(
    name: String,
    description: String,
    navController: NavHostController,
    viewModel: AddNewPlantTypeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var txtPlantName by remember { mutableStateOf("") }
    var txtPlantDescription by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            EditTextField(
                labelText = "Name",
                value = state.name,
                onValueChanged = (viewModel::changedName)
            )

            EditTextField(
                labelText = "Description",
                value = state.description,
                onValueChanged = (viewModel::changedDescription)
            )
        }

        Button(
            onClick = {
                if (state.name.isBlank()) {
                    Toast.makeText(context, "Fill plant type name", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (state.description.isBlank()) {
                    Toast.makeText(context, "Fill plant type description", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }
                navController.navigate("frequencyOfCare")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = getButtonColors()
        ) {
            Text(text = "NEXT", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}