package com.artushock.apps.spillme.ui.addplant.plant

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.extensions.shortToast
import com.artushock.apps.spillme.repositories.models.plants.PlantLocation
import com.artushock.apps.spillme.ui.addplant.plant.model.AddPlantScreenState
import com.artushock.apps.spillme.ui.base.IconPlus
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.colors.getTextFieldColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.model.ViewState
import com.artushock.apps.spillme.ui.navigation.NAV_ADD_NEW_PLANT_TYPE
import com.artushock.apps.spillme.ui.theme.MainBrown
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Composable
fun AddNewPlantScreen(
    navController: NavHostController,
    viewModel: AddNewPlantViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    val stateValue = state.value

    when (stateValue) {
        is ViewState.Error -> ErrorScreen(
            stateValue.error.message ?: "Error"
        ) { navController.navigateUp() }

        is ViewState.Loading -> ProgressScreen()

        is ViewState.Success -> ContentScreen(
            state = stateValue.result,
            onModifiedState = { viewModel.stateChanged(it) },
            onAddPlantType = { navController.navigate(NAV_ADD_NEW_PLANT_TYPE) },
            onAddLocation = {},
            onSaveClick = { stateToSave ->
                viewModel.addPlant(stateToSave).invokeOnCompletion { throwable ->
                    if (throwable == null) {
                        context.shortToast("Added new plant ${stateToSave.name}")
                        navController.navigateUp()
                    } else {
                        context.shortToast("Error ${throwable.message}")
                    }
                }

            },
        )
    }
}

@Composable
fun ContentScreen(
    state: AddPlantScreenState,
    onModifiedState: (AddPlantScreenState) -> Unit,
    onAddPlantType: () -> Unit,
    onAddLocation: () -> Unit,
    onSaveClick: (AddPlantScreenState) -> Unit,
) {
    val context = LocalContext.current

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
                value = state.name,
                onValueChanged = { onModifiedState(state.copy(name = it)) })

            EditTextField(labelText = "Description",
                value = state.description,
                onValueChanged = { onModifiedState(state.copy(description = it)) })

            DatePickerEditView(state.date.millis) {
                onModifiedState(state.copy(date = DateTime(it)))
            }



            DropDownEditText(
                label = "Plant type",
                options = state.plantTypes,
                selectedItem = state.plantType,
                onChangeSelectionOption = { onModifiedState(state.copy(plantType = it)) },
                onAddItem = onAddPlantType,
            )

            DropDownEditText(
                label = "Location",
                options = emptyList<PlantLocation>(),
                selectedItem = state.location,
                onChangeSelectionOption = { onModifiedState(state.copy(location = it)) },
                onAddItem = onAddLocation,
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp), onClick = { onSaveClick(state) },
            colors = getButtonColors()
        ) {
            IconPlus(20)
            Text(text = "ADD", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}

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
    label: String,
    options: List<T>,
    selectedItem: T,
    onChangeSelectionOption: (T) -> Unit,
    onAddItem: () -> Unit,
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
                label = { Text(label) },
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

            IconButton(onClick = { onAddItem() }) {
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

@Preview()
@Composable
fun ErrorScreen(
    message: String = "",
    onButtonClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message)
        Button(onClick = { onButtonClick() }) {
            Text(text = "OK")
        }
    }
}

@Preview
@Composable
fun ProgressScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
