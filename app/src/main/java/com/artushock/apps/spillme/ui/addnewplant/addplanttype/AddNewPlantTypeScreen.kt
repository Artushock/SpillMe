package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantType
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantTypeStep
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.UiState
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField

@Composable
fun AddNewPlantTypeScreen(
    navController: NavHostController,
    viewModel: AddNewPlantTypeViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            val context = LocalContext.current
            val state: UiState<NewPlantType> by viewModel.state.collectAsState()

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
                            onNameChanged = viewModel::changedName,
                            onDescriptionChanged = viewModel::changedDescription
                        )

                        NewPlantTypeStep.SECOND_STEP -> FrequencyOfCareScreen(
                            plantType = plantType,
                            onAddFertilizer = viewModel::addFertilizer
                        )

                        NewPlantTypeStep.LAST_STEP -> TODO()
                    }
                }

                is UiState.Error -> Error(
                    throwable = (state as UiState.Error).throwable
                )

                UiState.Loading -> Progress()
            }
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
            Text(text = "NEXT", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun FirstStep(
    name: String,
    nameError: Boolean,
    description: String,
    descriptionError: Boolean,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
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
}

@Composable
fun Progress() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun Error(throwable: Throwable) {
    throwable.message?.let { Text(text = it) }
}
