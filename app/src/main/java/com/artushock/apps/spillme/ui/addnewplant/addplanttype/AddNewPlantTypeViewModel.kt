package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.db.entities.CareFrequencyEntity
import com.artushock.apps.spillme.db.entities.ConditionsEntity
import com.artushock.apps.spillme.db.entities.FertilizerEntity
import com.artushock.apps.spillme.db.entities.PlantTypeEntity
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Fertilizer
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantType
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantTypeStep
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.PlantTypeCare
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
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

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> get() = _exitChannel


    fun changedName(name: String) {
        success(plantType.copy(name = name, nameError = false))
    }

    fun changedDescription(description: String) {
        success(plantType.copy(description = description, descriptionError = false))
    }

    fun addFertilizer(fertilizer: Fertilizer) {
        val editFertilizers = ArrayList(plantType.fertilizers)
        editFertilizers.add(fertilizer)
        success(plantType.copy(fertilizers = editFertilizers))
    }

    fun nextStep() {
        when (plantType.step) {
            NewPlantTypeStep.FIRST_STEP -> goToSecondStep()
            NewPlantTypeStep.SECOND_STEP -> savePlantType()
        }
    }

    private fun savePlantType() {
        loading()
        viewModelScope.launch {
            val careId = plantRepository.addCare(CareFrequencyEntity(plantType.care))
            plantType.fertilizers.forEach { fertilizer: Fertilizer ->
                plantRepository.addFertilizer(FertilizerEntity(fertilizer), careId.toInt())
            }
            val conditionsId = plantRepository.addConditions(ConditionsEntity(plantType.conditions))
            plantRepository.addPlantType(
                PlantTypeEntity(
                    name = plantType.name,
                    description = plantType.description,
                    careId = careId.toInt(),
                    conditionsId = conditionsId.toInt(),
                )
            )
        }.invokeOnCompletion { exit() }
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

    fun setTemperature(minTemp: Int, maxTemp: Int) {
        plantType = plantType.copy(
            conditions = plantType.conditions.copy(
                minTemp = minTemp,
                maxTemp = maxTemp
            )
        )
        success(plantType)
    }

    fun setHumidity(minHumidity: Int, maxHumidity: Int) {
        plantType = plantType.copy(
            conditions = plantType.conditions.copy(
                minHumidity = minHumidity,
                maxHumidity = maxHumidity
            )
        )
        success(plantType)
    }

    fun changeCare(care: PlantTypeCare) {
        plantType = plantType.copy(
            care = care
        )
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

    private fun exit() {
        viewModelScope.launch { _exitChannel.send(Unit) }
    }
}