package com.artushock.apps.spillme.repositories.models.plants

data class CareFrequency(
    val id: Int,
    val wateringFrequency: Int?,
    val sprayingFrequency: Int?,
    val rubbingFrequency: Int?,
    val transplantingFrequency: Int?,
    val bathingFrequency: Int?,
)
