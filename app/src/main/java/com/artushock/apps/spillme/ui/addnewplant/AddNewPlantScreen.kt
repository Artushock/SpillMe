package com.artushock.apps.spillme.ui.addnewplant

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.repositories.models.PlantLocation
import com.artushock.apps.spillme.repositories.models.PlantModel
import com.artushock.apps.spillme.repositories.models.PlantType
import com.artushock.apps.spillme.ui.base.IconPlus
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.colors.getTextFieldColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.theme.MainBrown
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Composable
fun AddNewPlant(
    navController: NavHostController,
    viewModel: AddNewPlantViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var txtPlantName by rememberSaveable { mutableStateOf("") }
    var txtPlantDescription by rememberSaveable { mutableStateOf("") }

    val dateTime = DateTime.now()
    var selectedDate by remember {
        mutableLongStateOf(dateTime.millis)
    }

    val plantTypeOptions = getPlantTypes()
    var selectedPlantType by remember { mutableStateOf(plantTypeOptions[0]) }

    val locationOptions = getPlantLocations()
    var selectedLocation by remember { mutableStateOf(locationOptions[0]) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {

            Image(painter = painterResource(id = R.drawable.add_photo_128),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        Toast
                            .makeText(context, "Add image", Toast.LENGTH_SHORT)
                            .show()
                    })

            EditTextField(labelText = "Name",
                value = txtPlantName,
                onValueChanged = { txtPlantName = it })

            EditTextField(labelText = "Description",
                value = txtPlantDescription,
                onValueChanged = { txtPlantDescription = it })

            DatePickerEditView(selectedDate) {
                selectedDate = it
            }

            DropDownEditText(navController, "Plant type", plantTypeOptions, selectedPlantType) {
                selectedPlantType = it
            }

            DropDownEditText(navController, "Location", locationOptions, selectedLocation) {
                selectedLocation = it
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp), onClick = {
                viewModel.addPlant(
                    PlantModel(
                        id = 0,
                        name = txtPlantName,
                        description = txtPlantDescription,
                        plantDate = DateTime(selectedDate),
                        plantType = selectedPlantType,
                        location = selectedLocation,
                    )
                ).invokeOnCompletion {
                    Toast.makeText(context, "Added new plant $txtPlantName", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigateUp()
                }
            }, colors = getButtonColors()
        ) {
            IconPlus(20)
            Text(text = "ADD", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }


}

fun getPlantLocations(): List<PlantLocation> = listOf(
    PlantLocation(
        id = 1,
        name = "Location #1",
        description = "Description of Location #1",
    ),
    PlantLocation(
        id = 2,
        name = "Location #2",
        description = "Description of Location #2",
    ),
    PlantLocation(
        id = 3,
        name = "Location #3",
        description = "Description of Location #3",
    )
)


fun getPlantTypes(): List<PlantType> = listOf(
    PlantType(
        id = 1,
        name = "Plant #1",
        type = 1,
        description = "Description of Plant #1",
    ),
    PlantType(
        id = 2,
        name = "Plant #2",
        type = 1,
        description = "Description of Plant #2",
    ),
    PlantType(
        id = 3,
        name = "Plant #3",
        type = 1,
        description = "Description of Plant #3",
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerEditView(selectedDate: Long, onPlantingDateChanged: (Long) -> Unit) {

    val dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy")
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)

    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }

    if (showDatePickerDialog) {
        androidx.compose.material3.DatePickerDialog(
            onDismissRequest = {
                showDatePickerDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    onPlantingDateChanged(datePickerState.selectedDateMillis!!)
                    showDatePickerDialog = false
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            DatePicker(state = datePickerState)
        }
    }

    OutlinedTextField(
        value = dateTimeFormatter.print(selectedDate),
        onValueChange = { },
        label = { Text(text = "Date") },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                colorFilter = ColorFilter.tint(MainBrown),
                contentDescription = null
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .onFocusChanged {
                showDatePickerDialog = it.hasFocus
            },
        colors = getTextFieldColors(),
        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
    )
}

@Composable
fun <T> DropDownEditText(
    navController: NavHostController,
    labelText: String,
    options: List<T>,
    selectedItem: T,
    onChangeSelectionOption: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedItem.toString(),
                onValueChange = { expanded = true },
                label = { Text(labelText) },
                modifier = Modifier
                    .weight(1.0F)
                    .padding(0.dp, 0.dp, 16.dp, 0.dp),
                trailingIcon = {
                    Icon(imageVector = icon,
                        contentDescription = "contentDescription",
                        tint = MainBrown,
                        modifier = Modifier.clickable { expanded = !expanded })
                },
                colors = getTextFieldColors(),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            IconButton(onClick = { navController.navigate("addNewPlantType") }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_button_48),
                    contentDescription = null
                )
            }
        }

        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White),
            content = {
                options.forEach { option: T ->
                    DropdownMenuItem(onClick = {
                        onChangeSelectionOption(option)
                        expanded = false
                    }, text = {
                        Text(
                            text = option.toString(),
                            color = MainBrown,
                        )
                    })
                }
            })
    }
}
