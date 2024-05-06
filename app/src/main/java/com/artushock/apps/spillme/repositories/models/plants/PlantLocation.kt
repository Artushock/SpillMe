package com.artushock.apps.spillme.repositories.models.plants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantLocation(
    val id: Int,
    val name: String,
    val description: String,
): Parcelable {
    override fun toString() = name
}
