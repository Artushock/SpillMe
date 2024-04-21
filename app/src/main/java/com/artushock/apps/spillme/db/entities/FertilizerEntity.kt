package com.artushock.apps.spillme.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artushock.apps.spillme.ui.addnewplant.addplanttype.model.Fertilizer

@Entity(tableName = "fertilizer")
data class FertilizerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val frequency: Int,
) {
    constructor(domain: Fertilizer) : this(
        id = 0,
        name = domain.name,
        frequency = domain.frequency
    )
}