package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Lighting
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantType
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantTypeStep
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.UiState
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.base.sliders.RangeValuesSlider
import com.artushock.apps.spillme.ui.theme.MainBrown
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun AddNewPlantTypeScreen(
    navController: NavHostController,
    viewModel: AddNewPlantTypeViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val state: UiState<NewPlantType> by viewModel.state.collectAsState()

    val exit = viewModel.exitChannel.receiveAsFlow()
    LaunchedEffect(1) {
        exit.collectLatest {
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
                .verticalScroll(scrollState)
        ) {
            when (state) {
                is UiState.Success -> {
                    val plantType: NewPlantType = (state as UiState.Success<NewPlantType>).data

                    if (plantType.message != null) {
                        Toast.makeText(
                            context,
                            plantType.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.messageShown()
                    }

                    when (plantType.step) {
                        NewPlantTypeStep.FIRST_STEP -> FirstStep(
                            name = plantType.name,
                            nameError = plantType.nameError,
                            description = plantType.description,
                            descriptionError = plantType.descriptionError,
                            minTemp = plantType.conditions.minTemp,
                            maxTemp = plantType.conditions.maxTemp,
                            minHumidity = plantType.conditions.minHumidity,
                            maxHumidity = plantType.conditions.maxHumidity,
                            lighting = plantType.conditions.lighting,
                            onNameChanged = viewModel::changedName,
                            onDescriptionChanged = viewModel::changedDescription,
                            onTemperatureChanged = viewModel::setTemperature,
                            onHumidityChanged = viewModel::setHumidity
                        )

                        NewPlantTypeStep.SECOND_STEP -> FrequencyOfCareScreen(
                            plantType = plantType,
                            onCareChanged = viewModel::changeCare,
                            onAddFertilizer = viewModel::addFertilizer
                        )
                    }
                }

                is UiState.Error -> Error(
                    throwable = (state as UiState.Error).throwable
                )

                UiState.Loading -> Progress()
            }

            Spacer(modifier = Modifier.height(50.dp))
        }

        Button(
            onClick = {
                viewModel.nextStep()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            colors = getButtonColors()
        ) {
            val buttonText =
                if (state is UiState.Success && (state as UiState.Success<NewPlantType>).data.step == NewPlantTypeStep.SECOND_STEP) "FINISH" else "NEXT"
            Text(text = buttonText, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun FirstStep(
    name: String,
    nameError: Boolean,
    description: String,
    descriptionError: Boolean,
    minTemp: Int,
    maxTemp: Int,
    minHumidity: Int,
    maxHumidity: Int,
    lighting: Lighting,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onTemperatureChanged: (Int, Int) -> Unit,
    onHumidityChanged: (Int, Int) -> Unit,
) {
    var txtPlantName by remember { mutableStateOf(name) }
    var txtPlantDescription by remember { mutableStateOf(description) }

    EditTextField(
        labelText = "Name",
        value = txtPlantName,
        isError = nameError,
        onValueChanged = { text ->
            txtPlantName = text
            onNameChanged(text)
        },
    )

    EditTextField(
        labelText = "Description",
        value = txtPlantDescription,
        isError = descriptionError,
        onValueChanged = { text ->
            txtPlantDescription = text
            onDescriptionChanged(text)
        }
    )

    Spacer(modifier = Modifier.height(32.dp))

    RangeValuesSlider(
        isCheckboxVisible = false,
        name = "Temperature",
        units = "°C",
        minValue = -20,
        maxValue = 50,
        defaultValue = minTemp.toFloat()..maxTemp.toFloat(),
        valueChangeListener = { range ->
            range?.let {
                onTemperatureChanged(it.start.toInt(), it.endInclusive.toInt())
            }
        },
    )

    RangeValuesSlider(
        isCheckboxVisible = false,
        name = "Humidity",
        units = "%",
        minValue = 0,
        maxValue = 100,
        defaultValue = minHumidity.toFloat()..maxHumidity.toFloat(),
        valueChangeListener = { range ->
            range?.let {
                onHumidityChanged(it.start.toInt(), it.endInclusive.toInt())
            }
        },
    )

    var selectedOption by remember {
        mutableStateOf(lighting)
    }
    Column {

        Text(
            text = "Lighting: ",
            modifier = Modifier
                .padding(8.dp),
            style = TextStyle(color = MainBrown, fontSize = 16.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Lighting.entries.forEach { option ->
            Row {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { selectedOption = option })
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = option.title
                )
            }
        }
    }

}

@Composable
fun Progress() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun Error(throwable: Throwable) {
    throwable.message?.let { Text(text = it) }
}
