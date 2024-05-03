package com.artushock.apps.spillme.ui.addplant.plant.model

import android.os.Parcelable
import com.artushock.apps.spillme.repositories.models.plants.PlantLocation
import com.artushock.apps.spillme.repositories.models.plants.PlantType
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
data class AddPlantScreenState(
    val name: String = "",
    val description: String = "",
    val date: DateTime = DateTime.now(),
    val plantTypes: List<PlantType> = emptyList(),
    val plantType: PlantType? = null,
    val locations: List<PlantLocation> = emptyList(),
    val location: PlantLocation? = null,
) : Parcelable
