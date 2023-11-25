package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.ui.base.colors.getButtonColors

import com.artushock.apps.spillme.ui.base.edittext.EditTextField

@Composable
fun AddNewPlantTypeScreen(
    navController: NavHostController,
) {
    var txtPlantName by rememberSaveable { mutableStateOf("") }
    var txtPlantDescription by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            EditTextField(labelText = "Name",
                value = txtPlantName,
                onValueChanged = { txtPlantName = it })

            EditTextField(labelText = "Description",
                value = txtPlantDescription,
                onValueChanged = { txtPlantDescription = it })
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp), onClick = { /*todo*/ }, colors = getButtonColors()
        ) {
            Text(text = "NEXT", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}