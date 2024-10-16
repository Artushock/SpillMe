package com.artushock.apps.spillme.repositories.models.plants

data class PlantLocation(
    val id: Int,
    val name: String,
    val description: String,
) {
    override fun toString() = name
}
