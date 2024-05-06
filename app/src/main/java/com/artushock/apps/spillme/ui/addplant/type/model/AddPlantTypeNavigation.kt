package com.artushock.apps.spillme.ui.addplant.type.model

enum class AddPlantTypeNavigation(private val id: Int) {
    WRONG(-1),
    BASIC_DESCRIPTION(1),
    FREQUENCY_OF_CARE(2);

    val screenId get() = id

    companion object {
        fun getScreenById(id: Int): AddPlantTypeNavigation = try {
            entries[id]
        } catch (e: Throwable) {
            WRONG
        }
    }
}