package com.artushock.apps.spillme.ui.addnewplant.addplanttype.model

data class NewPlantType(
    val step: NewPlantTypeStep = NewPlantTypeStep.FIRST_STEP,
    val message: String? = null,
    val name: String = "",
    val nameError: Boolean = false,
    val description: String = "",
    val descriptionError: Boolean = false,

    val minTemp: Int = 10,
    val maxTemp: Int = 30,
    val minHumidity: Int = 10,
    val maxHumidity: Int = 90,

    val wateringFrequency: Int = 3,
    val sprayingFrequency: Int = 12,
    val rubbingFrequency: Int = 1,
    val transplantingFrequency: Int = 12,
    val bathingFrequency: Int = 18,
    val fertilizers: List<Fertilizer> = emptyList()
)

enum class NewPlantTypeStep {
    FIRST_STEP,
    SECOND_STEP,
    LAST_STEP
}