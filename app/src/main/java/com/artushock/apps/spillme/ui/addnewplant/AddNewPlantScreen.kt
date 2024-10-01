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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.repositories.models.PlantLocation
import com.artushock.apps.spillme.repositories.models.PlantType
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.Progress
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.UiState
import com.artushock.apps.spillme.ui.addnewplant.models.PlantUIModel
import com.artushock.apps.spillme.ui.base.IconPlus
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.colors.getTextFieldColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.theme.MainBrown
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Composable
fun AddNewPlantScreen(
    navController: NavHostController,
    viewModel: AddNewPlantViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.plantTypeState.collectAsState()

    val exit = viewModel.exitChannel.receiveAsFlow()
    LaunchedEffect(1) {
        exit.collectLatest {
            Toast.makeText(context, "Added new plant", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {

            when (state) {
                UiState.Loading -> {
                    Progress()
                }

                is UiState.Success -> {
                    val plantUiModel: PlantUIModel = (state as UiState.Success<PlantUIModel>).data
                    AddNewPlantScreenSuccess(
                        plantUiModel = plantUiModel,
                        onNameChanged = viewModel::nameChanged,
                        onDescriptionChanged = viewModel::descriptionChanged,
                        onDateTimeChanged = viewModel::dateTimeChanged,
                        onPlantTypeChanged = viewModel::plantTypeChanged,
                        onNavigateToAddNewPlantType = { navController.navigate("addNewPlantType") },
                        onPlantLocationChanged = viewModel::plantLocationChanged,
                        onNavigateToAddNewPlantLocation = {/*TODO (Navigate to add new location)*/ },
                    )
                }

                is UiState.Error -> {
                    TODO()
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = viewModel::addPlant,
            colors = getButtonColors()
        ) {
            IconPlus(20)
            Text(text = "ADD", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }


}

@Composable
private fun AddNewPlantScreenSuccess(
    plantUiModel: PlantUIModel,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDateTimeChanged: (Long) -> Unit,
    onPlantTypeChanged: (PlantType) -> Unit,
    onNavigateToAddNewPlantType: () -> Unit,
    onPlantLocationChanged: (PlantLocation) -> Unit,
    onNavigateToAddNewPlantLocation: () -> Unit,
) {
    var uiState by remember { mutableStateOf(plantUiModel) }

    Image(painter = painterResource(id = R.drawable.add_photo_128),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // todo (Click on image button)
            })

    EditTextField(labelText = "Name",
        value = uiState.name,
        isError = plantUiModel.nameError,
        onValueChanged = { name ->
            uiState = uiState.copy(name = name)
            onNameChanged(name)
        })

    EditTextField(labelText = "Description",
        value = uiState.description,
        isError = plantUiModel.descriptionError,
        onValueChanged = { description ->
            uiState = uiState.copy(description = description)
            onDescriptionChanged(description)
        })

    DatePickerEditView(uiState.dateTime.millis) { timestamp ->
        uiState = uiState.copy(dateTime = DateTime(timestamp))
        onDateTimeChanged(timestamp)
    }

    DropDownEditText(
        labelText = "Plant type",
        options = uiState.plantTypeList,
        selectedItem = uiState.selectedPlantType,
        onChangeSelectionOption = { plantType ->
            uiState = uiState.copy(selectedPlantType = plantType)
            onPlantTypeChanged(plantType)
        },
        navigateToAdd = onNavigateToAddNewPlantType,
    )

    DropDownEditText(
        labelText = "Location",
        options = uiState.locationList,
        selectedItem = uiState.selectedLocation,
        onChangeSelectionOption = { location ->
            uiState = uiState.copy(selectedLocation = location)
            onPlantLocationChanged(location)
        },
        navigateToAdd = onNavigateToAddNewPlantLocation,
    )
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
    labelText: String,
    options: List<T>,
    selectedItem: T?,
    onChangeSelectionOption: (T) -> Unit,
    navigateToAdd: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val isEnabled = options.isNotEmpty() && selectedItem != null

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
                value = if (isEnabled) selectedItem.toString() else "",
                onValueChange = { expanded = true },
                label = { Text(labelText) },
                modifier = Modifier
                    .weight(1.0F)
                    .padding(0.dp, 0.dp, 16.dp, 0.dp),
                enabled = isEnabled,
                trailingIcon = {
                    Icon(imageVector = icon,
                        contentDescription = "contentDescription",
                        tint = MainBrown,
                        modifier = Modifier.clickable { expanded = !expanded })
                },
                colors = getTextFieldColors(),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            IconButton(onClick = { navigateToAdd.invoke() }) {
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
