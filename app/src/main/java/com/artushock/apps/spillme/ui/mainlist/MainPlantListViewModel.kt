package com.artushock.apps.spillme.ui.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.ui.mainlist.models.MainListPlantModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPlantListViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
) : ViewModel() {


    private val _plants = MutableStateFlow(emptyList<MainListPlantModel>())
    val plants: StateFlow<List<MainListPlantModel>> get() = _plants

    init {
        getPlants()
    }

    private fun getPlants() {
        viewModelScope.launch {
            plantRepository.getAllPlants().flowOn(Dispatchers.IO).collect { entities ->
                _plants.update {
                    entities.map { plantModel ->
                        MainListPlantModel(
                            localId = plantModel.id,
                            name = plantModel.name,
                            nextWatering = 10, //todo
                            nextFeeding = 11, //todo
                            nextSpraying = 12, //todo
                            photo = plantModel.photo,
                        )
                    }
                }
            }
        }
    }

    fun removeItem(plantId: Int) = viewModelScope.launch {
        plantRepository.removePlant(plantId)
    }.invokeOnCompletion {
        if (it == null) getPlants()
    }
}