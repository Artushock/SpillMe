package com.artushock.apps.spillme.ui.addplant.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.ui.addplant.type.model.AddPlantTypeNavigation
import com.artushock.apps.spillme.ui.addplant.type.model.AddTypeScreenState

@Composable
fun AddPlantTypeContainer(
    navController: NavHostController,
    viewModel: AddNewPlantTypeViewModel = hiltViewModel(),
) {
    val shownScreenId = rememberSaveable {
        mutableIntStateOf(1)
    }

    val screenState = rememberSaveable {
        mutableStateOf(AddTypeScreenState())
    }

    when (AddPlantTypeNavigation.getScreenById(shownScreenId.intValue)) {
        AddPlantTypeNavigation.WRONG -> {/*TODO*/}
        AddPlantTypeNavigation.BASIC_DESCRIPTION -> AddBasicDescription(
            state = screenState.value,
            onStateModified = { screenState.value = it },
            onNext = { shownScreenId.intValue = AddPlantTypeNavigation.FREQUENCY_OF_CARE.screenId }
        )

        AddPlantTypeNavigation.FREQUENCY_OF_CARE -> FrequencyOfCareScreen(
            state = screenState.value,
            onStateModified = { screenState.value = it },
            onNext = {
                viewModel.savePlantType(screenState.value)
                    .invokeOnCompletion { navController.navigateUp() }
            }
        )
    }
}