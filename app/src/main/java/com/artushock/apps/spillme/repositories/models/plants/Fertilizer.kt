package com.artushock.apps.spillme.repositories.models.plants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fertilizer(
    val name: String,
    val frequency: Int,
): Parcelable