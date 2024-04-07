package com.artushock.apps.spillme.repositories.models

data class PlantType(
    val id: Int,
    val name: String, 
    val type: Int,
    val description: String,
) {
    override fun toString() = name
}
