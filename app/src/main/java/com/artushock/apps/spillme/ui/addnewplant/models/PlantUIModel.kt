package com.artushock.apps.spillme.ui.addnewplant.models


import com.artushock.apps.spillme.repositories.models.plants.PlantLocation
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import org.joda.time.DateTime

data class PlantUIModel(
    val name: String = "",
    val nameError: Boolean = false,
    val description: String = "",
    val descriptionError: Boolean = false,
    val dateTime: DateTime = DateTime.now(),
    val image: String = "",
    val plantTypeList: List<PlantType> = emptyList(),
    val selectedPlantType: PlantType? = null,
    val locationList: List<PlantLocation> = emptyList(),
    val selectedLocation: PlantLocation? = null,
)
