package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Fertilizer
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantType
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantTypeStep
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewPlantTypeViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
) : ViewModel() {

    private var plantType = NewPlantType()
    private val _state = MutableStateFlow<UiState<NewPlantType>>(UiState.Success(plantType))
    val state: StateFlow<UiState<NewPlantType>> get() = _state

    fun changedName(name: String) {
        success(plantType.copy(name = name, descriptionError = false))
    }

    fun changedDescription(description: String) {
        success(plantType.copy(description = description, descriptionError = false))
    }

    fun addFertilizer(fertilizer: Fertilizer) {
        val editFertilizers = ArrayList(plantType.fertilizers)
        editFertilizers.add(fertilizer)
        success(plantType.copy(fertilizers = editFertilizers))
    }

    private fun loading() = viewModelScope.launch {
        _state.value = UiState.Loading
    }

    private fun error(throwable: Throwable) = viewModelScope.launch {
        _state.value = UiState.Error(throwable)
    }

    private fun success(newPlantType: NewPlantType) {
        plantType = newPlantType
        _state.value = UiState.Success(plantType)
    }

    fun nextStep() {
        when (plantType.step) {
            NewPlantTypeStep.FIRST_STEP -> goToSecondStep()
            NewPlantTypeStep.SECOND_STEP -> TODO()
            NewPlantTypeStep.LAST_STEP -> TODO()
        }
    }

    private fun goToSecondStep() {
        loading()
        if (plantType.name.isBlank()) {
            plantType = plantType.copy(nameError = true)
            message("Fill plant type name")
            return
        }
        if (plantType.description.isBlank()) {
            plantType = plantType.copy(descriptionError = true)
            message("Fill plant type description")
            return
        }

        plantType = plantType.copy(step = NewPlantTypeStep.SECOND_STEP)
        _state.update { UiState.Success(plantType) }
    }

    private fun message(message: String) {
        success(plantType.copy(message = message))
    }

    fun messageShown() {
        success(plantType.copy(message = null))
    }
}