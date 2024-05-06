package com.artushock.apps.spillme.repositories.models.plants

import android.os.Parcelable
import com.artushock.database.entities.PlantTypeEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantType(
    val id: Int,
    val name: String,
    val description: String,
    val careId: Int?,
    val conditionsId: Int?,
) : Parcelable {
    constructor(entity: PlantTypeEntity) : this(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        careId = entity.careId,
        conditionsId = entity.conditionsId,
    )

    override fun toString() = name
}
