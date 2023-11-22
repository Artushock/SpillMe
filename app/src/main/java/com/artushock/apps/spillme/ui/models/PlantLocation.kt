package com.artushock.apps.spillme.ui.models

data class PlantLocation(
    val id: Int,
    val name: String,
    val description: String,
) {
    override fun toString() = name
}
