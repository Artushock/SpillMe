package com.artushock.apps.spillme.ui.addnewplant

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.repositories.models.PlantLocation
import com.artushock.apps.spillme.repositories.models.PlantModel
import com.artushock.apps.spillme.repositories.models.PlantType
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.UiState
import com.artushock.apps.spillme.ui.addnewplant.models.PlantUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class AddNewPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private var plantModel = PlantUIModel()
    private val _plantTypeState: MutableStateFlow<UiState<PlantUIModel>> =
        MutableStateFlow(UiState.Loading)
    val plantTypeState: StateFlow<UiState<PlantUIModel>> get() = _plantTypeState

    private val _exitChannel = Channel<Unit>()
    val exitChannel: ReceiveChannel<Unit> get() = _exitChannel

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch {
            val plantTypeList = plantRepository.getPlantTypes()
            val locations = getLocations()

            _plantTypeState.update {
                UiState.Success(
                    plantModel.copy(
                        plantTypeList = plantTypeList,
                        selectedPlantType = if (plantTypeList.isNotEmpty()) plantTypeList[0] else null,
                        locationList = locations,
                        selectedLocation = if (locations.isNotEmpty()) locations[0] else null,
                    )
                )
            }
        }
    }

    private fun getLocations() = emptyList<PlantLocation>()

    fun addPlant() = viewModelScope.launch {
        val plantModel = PlantModel(
            id = 0,
            name = plantModel.name,
            description = plantModel.description,
            plantDate = plantModel.dateTime,
            plantType = plantModel.selectedPlantType,
            location = plantModel.selectedLocation,
            photo = plantModel.image
        )
        plantRepository.addPlant(PlantEntity(plantModel))
    }.invokeOnCompletion { exit() }

    fun nameChanged(name: String) {
        plantModel = plantModel.copy(name = name)
    }

    fun descriptionChanged(description: String) {
        plantModel = plantModel.copy(description = description)
    }

    fun dateTimeChanged(timestamp: Long) {
        plantModel = plantModel.copy(dateTime = DateTime(timestamp))
    }

    fun plantTypeChanged(plantType: PlantType) {
        plantModel = plantModel.copy(selectedPlantType = plantType)
    }

    fun plantLocationChanged(plantLocation: PlantLocation) {
        plantModel = plantModel.copy(selectedLocation = plantLocation)
    }

    private fun exit() {
        viewModelScope.launch { _exitChannel.send(Unit) }
    }

    fun imageUriChanged(uri: Uri) {
        plantModel = plantModel.copy(image = uri.toString())
    }
}