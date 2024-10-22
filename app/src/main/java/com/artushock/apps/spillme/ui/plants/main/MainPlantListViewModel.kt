package com.artushock.apps.spillme.ui.plants.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.db.selectors.PlantFull
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.repositories.PrefsRepository
import com.artushock.apps.spillme.ui.plants.main.models.MainListPlantModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@HiltViewModel
class MainPlantListViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    private val prefsRepository: PrefsRepository,
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
                    entities.map { plant: PlantFull ->
                        val plantDate = DateTime(plant.plant.plantDate)
                        val care  = plant.plantType?.careFrequencyEntity
                        MainListPlantModel(
                            localId = plant.plant.id,
                            name = plant.plant.name,
                            nextWatering = daysUntilNext(plantDate, frequencyDays = care?.wateringFrequency),
                            nextFeeding = daysUntilNext(plantDate, frequencyDays = care?.bathingFrequency),
                            nextSpraying = daysUntilNext(plantDate, frequencyDays = care?.sprayingFrequency),
                            photo = plant.plant.photoUri,
                        )
                    }
                }
            }
        }
    }

    fun clearAuthData() {
        prefsRepository.setPassword("")
        prefsRepository.setLogin("")
    }
}

fun daysUntilNext(firstDate: DateTime, frequencyDays: Int?): Int {
    if (frequencyDays == null) return 1
    val today = DateTime.now()
    val daysSinceFirstWatering = Days.daysBetween(firstDate.toLocalDate(), today.toLocalDate()).days
    val daysSinceLastWatering = daysSinceFirstWatering % frequencyDays
    val daysUntilNextWatering = frequencyDays - daysSinceLastWatering

    return if (daysUntilNextWatering == frequencyDays) 0 else daysUntilNextWatering
}