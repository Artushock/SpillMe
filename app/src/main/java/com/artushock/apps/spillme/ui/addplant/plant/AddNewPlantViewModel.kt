package com.artushock.apps.spillme.ui.addplant.plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.repositories.models.plants.PlantModel
import com.artushock.apps.spillme.ui.addplant.plant.model.AddPlantScreenState
import com.artushock.apps.spillme.ui.model.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNewPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ViewState<AddPlantScreenState>> =
        MutableStateFlow(ViewState.Loading)
    val state: StateFlow<ViewState<AddPlantScreenState>> get() = _state

    init {
        initScreen()
    }

    private fun initScreen() = viewModelScope.launch {
        _state.emit(ViewState.Loading)
        plantRepository.getPlantTypes()
            .flowOn(Dispatchers.IO)
            .catch {
                _state.emit(ViewState.Error(it))
            }
            .collect {
                _state.emit(
                    ViewState.Success(
                        AddPlantScreenState().copy(
                            plantTypes = it,
                            plantType = it[0]
                        )
                    )
                )
            }
    }

    fun addPlant(addPlantScreenState: AddPlantScreenState) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            plantRepository.addPlant(
                PlantModel(
                    id = 0,
                    name = addPlantScreenState.name,
                    description =addPlantScreenState.description,
                    plantDate = addPlantScreenState.date,
                    plantType = addPlantScreenState.plantType,
                    location = addPlantScreenState.location,
                    photo = "https://images.unsplash.com/photo-1597305877032-0668b3c6413a?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                )
            )
        }
    }

    fun stateChanged(state: AddPlantScreenState) = viewModelScope.launch {
        _state.emit(ViewState.Success(state))
    }
}