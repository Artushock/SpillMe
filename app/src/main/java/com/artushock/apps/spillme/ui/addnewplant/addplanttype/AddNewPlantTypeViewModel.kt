package com.artushock.apps.spillme.ui.addnewplant.addplanttype

import androidx.lifecycle.ViewModel
import com.artushock.apps.spillme.repositories.PlantRepository
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Fertilizer
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.NewPlantType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddNewPlantTypeViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(NewPlantType("", ""))
    val state: StateFlow<NewPlantType> get() = _state

    fun changedName(name: String) {
        val editState = _state.value
        _state.update { editState.copy(name = name) }
    }

    fun changedDescription(description: String) {
        val editState = _state.value
        _state.update { editState.copy(description = description) }
    }

    fun addFertilizer(fertilizer: Fertilizer) {
        val editState = _state.value
        val editFertilizers = ArrayList(editState.fertilizers)
        editFertilizers.add(fertilizer)
        _state.update { editState.copy(fertilizers = editFertilizers) }
    }
}