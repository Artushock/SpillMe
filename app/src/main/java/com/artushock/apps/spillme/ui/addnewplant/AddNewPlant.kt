package com.artushock.apps.spillme.ui.addnewplant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.ui.theme.MainBeige
import com.artushock.apps.spillme.ui.theme.MainBrown
import com.artushock.apps.spillme.ui.theme.MainGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPlant(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainBeige)
    ) {
        var txtPlantName by rememberSaveable { mutableStateOf("") }
        var txtPlantDescription by rememberSaveable { mutableStateOf("") }


        TextField(
            value = txtPlantName,
            onValueChange = { txtPlantName = it },
            label = { Text(text = "Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp, 8.dp, 4.dp),
            colors = textFieldColors(),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
        )
        TextField(
            value = txtPlantDescription,
            onValueChange = { txtPlantDescription = it },
            label = { Text(text = "Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp, 8.dp, 4.dp),
            colors = textFieldColors(),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun textFieldColors() = TextFieldDefaults.textFieldColors(
    containerColor = Color.White,
    focusedIndicatorColor = MainGreen,
    unfocusedIndicatorColor = MainBrown,
    cursorColor = MainGreen,
    textColor = MainGreen,
    focusedLabelColor = MainGreen,
    unfocusedLabelColor = MainBrown,
    unfocusedSupportingTextColor = MainGreen
)