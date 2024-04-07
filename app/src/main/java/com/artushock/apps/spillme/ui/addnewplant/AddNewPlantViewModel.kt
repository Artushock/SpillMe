package com.artushock.apps.spillme.ui.addnewplant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.apps.spillme.db.entities.PlantEntity
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.repositories.models.PlantModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
): ViewModel() {
    fun addPlant(plantModel: PlantModel) = viewModelScope.launch {
       plantRepository.addPlant(PlantEntity(plantModel))
    }
}