package com.artushock.apps.spillme.ui.addnewplant

import androidx.lifecycle.ViewModel
import com.artushock.apps.spillme.repositories.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNewPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
): ViewModel() {
    fun addPlant(name:String, description: String){
        Thread{
            try {
                plantRepository.addPlant(name, description)
            } catch (e: Exception){
                //
            }
        }.start()
    }
}