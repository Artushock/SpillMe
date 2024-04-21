package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.AddTypeScreenState
import com.artushock.apps.spillme.repositories.models.plants.CareFrequency
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNewPlantTypeViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
) : ViewModel() {

    fun savePlantType(state: AddTypeScreenState) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val careId = async { plantRepository.saveCare(getCareFrequency(state)) }.await()
            state.fertilizers.map {
                async { plantRepository.saveFertilizer(it) }
            }
                .map { it.await() }
                .forEach { fertilizerId ->
                    plantRepository.connectFertilizerWithCare(fertilizerId, careId)
                }
            plantRepository.savePlantType(
                PlantType(
                    id = 0,
                    name = state.name,
                    description = state.description,
                    careId = careId,
                    conditionsId = null, /*TODO (Add screen to add conditions)*/
                )
            )
        }
    }

    private fun getCareFrequency(state: AddTypeScreenState) = CareFrequency(
        id = 0,
        wateringFrequency = state.wateringFrequency,
        sprayingFrequency = state.sprayingFrequency,
        rubbingFrequency = state.rubbingFrequency,
        transplantingFrequency = state.transplantingFrequency,
        bathingFrequency = state.bathingFrequency,
    )
}