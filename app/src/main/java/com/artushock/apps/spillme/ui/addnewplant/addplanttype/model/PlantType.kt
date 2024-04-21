package com.artushock.apps.spillme.ui.addnewplant.addplanttype.model

data class PlantType(
    val id: Int,
    val name: String,
    val description: String,
    val careId: Int?,
    val conditionsId: Int?,
)
