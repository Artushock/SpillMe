package com.artushock.apps.spillme.ui.addnewplant

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.repositories.models.plants.PlantLocation
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.Progress
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.UiState
import com.artushock.apps.spillme.ui.addnewplant.models.PlantUIModel
import com.artushock.apps.spillme.ui.base.IconPlus
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.colors.getTextFieldColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.theme.MainBrown
import com.artushock.apps.spillme.utils.ImageSaveUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
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
    val imageSaveUtil = ImageSaveUtil(context)
    val state by viewModel.plantTypeState.collectAsState()

    val exit = viewModel.exitChannel.receiveAsFlow()
    LaunchedEffect(Unit) {
        viewModel.initState()
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
                        imageSaveUtil,
                        plantUiModel = plantUiModel,
                        onNameChanged = viewModel::nameChanged,
                        onDescriptionChanged = viewModel::descriptionChanged,
                        onDateTimeChanged = viewModel::dateTimeChanged,
                        onPlantTypeChanged = viewModel::plantTypeChanged,
                        onNavigateToAddNewPlantType = { navController.navigate("addNewPlantType") },
                        onPlantLocationChanged = viewModel::plantLocationChanged,
                        onNavigateToAddNewPlantLocation = {/*TODO (Navigate to add new location)*/ },
                        onImageUriChanged = viewModel::imageUriChanged
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AddNewPlantScreenSuccess(
    imageSaveUtil: ImageSaveUtil,
    plantUiModel: PlantUIModel,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDateTimeChanged: (Long) -> Unit,
    onPlantTypeChanged: (PlantType) -> Unit,
    onNavigateToAddNewPlantType: () -> Unit,
    onPlantLocationChanged: (PlantLocation) -> Unit,
    onNavigateToAddNewPlantLocation: () -> Unit,
    onImageUriChanged: (Uri) -> Unit,
) {
    var uiState by remember { mutableStateOf(plantUiModel) }
    var openDialog by remember { mutableStateOf(false) }

    var permissionsGranted by remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted -> permissionsGranted = isGranted }

    LaunchedEffect(cameraPermissionState) {
        if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
            // Show rationale if needed
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = imageSaveUtil.saveImageToInternalStorage(it, true)?.toUri()
                onImageUriChanged(imageUri ?: it)
            }

        }

    var tempUri by remember { mutableStateOf<Uri?>(null) }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        tempUri?.let {
            imageUri = imageSaveUtil.saveImageToInternalStorage(it)?.toUri()
            onImageUriChanged(imageUri ?: it)
        }
    }

    val imageModifier = Modifier
        .size(160.dp)
        .padding(16.dp)
        .clip(CircleShape)
        .clickable { openDialog = true }


    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else
            Image(
                painter = painterResource(id = R.drawable.add_photo_128),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
    }

    if (openDialog) {
        Dialog(onDismissRequest = { openDialog = false }) {
            Column {
                Button(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                        openDialog = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Select from gallery")
                }
                Button(
                    onClick = {
                        if (permissionsGranted) {
                            tempUri = imageSaveUtil.createUriForPhoto()
                            tempUri?.let(takePictureLauncher::launch)
                        }
                        openDialog = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = permissionsGranted
                ) {
                    Text(text = "Take a picture")
                }
            }
        }
    }

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
        onChangeSelectionOption = { plantType: PlantType ->
            uiState = uiState.copy(selectedPlantType = plantType)
            onPlantTypeChanged(plantType)
        },
        navigateToAdd = onNavigateToAddNewPlantType,
    )

    DropDownEditText(
        labelText = "Location",
        options = uiState.locationList,
        selectedItem = uiState.selectedLocation,
        onChangeSelectionOption = { location: PlantLocation ->
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
