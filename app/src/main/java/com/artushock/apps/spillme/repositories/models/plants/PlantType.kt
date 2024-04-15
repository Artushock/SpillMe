package com.artushock.apps.spillme.repositories.models.plants

data class PlantType(
    val id: Int,
    val name: String, 
    val type: Int,
    val description: String,
) {
    override fun toString() = name
}
