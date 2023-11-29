package com.artushock.apps.spillme.ui.addnewplant.addplanttype.model

data class NewPlantType(
    val name: String? = null,
    val description: String? = null,
    val wateringFrequency: Int = 3,
    val sprayingFrequency: Int = 12,
    val rubbingFrequency: Int = 1,
    val transplantingFrequency: Int = 12,
    val bathingFrequency: Int = 18,
    val fertilizers: List<Fertilizer> = emptyList()
)
