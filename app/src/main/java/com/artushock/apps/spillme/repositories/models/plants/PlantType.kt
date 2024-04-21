package com.artushock.apps.spillme.repositories.models.plants

data class PlantType(
    val id: Int,
    val name: String,
    val description: String,
    val careId: Int?,
    val conditionsId: Int?,
) {
    override fun toString() = name
}
