package com.artushock.apps.spillme.ui.mainlist
data class MainListPlantModel(
    val localId: Int,
    val name: String,
    val nextWatering: Int,
    val nextFeeding: Int,
    val nextSpraying: Int,
    val photo: String,
)
