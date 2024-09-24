package com.artushock.apps.spillme.ui.addnewplant.addplanttype.model

enum class Lighting(
    val id: Int,
    val title: String
) {
    SUNLIGHT(0, "Sunlight"),
    DIFFUSE_SUNLIGHT(1, "Diffuse sunlight"),
    DIFFUSE(2, "Diffuse"),
    PENUMBRA(3, "Penumbra"),
    SHADOW(4, "Shadow"),
}