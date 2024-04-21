package com.artushock.apps.spillme.ui.addnewplant.addplanttype.model

data class CareFrequency(
    val id: Int,
    val wateringFrequency: Int?,
    val sprayingFrequency: Int?,
    val rubbingFrequency: Int?,
    val transplantingFrequency: Int?,
    val bathingFrequency: Int?,
)
