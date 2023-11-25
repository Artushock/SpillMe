package com.artushock.apps.spillme.ui.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.ui.models.PlantModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class MainPlantListViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
) : ViewModel() {


    private val _plants = MutableStateFlow(emptyList<PlantModel>())
    val plants: StateFlow<List<PlantModel>> get() = _plants

    init {
        getPlants()
    }

    private fun getPlants() {
        viewModelScope.launch {
            plantRepository.getAllPlants().flowOn(Dispatchers.IO).collect { entities ->
                _plants.update {
                    entities.map {
                        PlantModel(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            plantDate = DateTime(it.plantDate),
                            plantType = null,
                            location = null,
                            photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
                        )
                    }
                }
            }
        }
    }
}