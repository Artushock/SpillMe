package com.artushock.apps.spillme.dev

import com.artushock.apps.spillme.ui.mainlist.Plant

class PlantsProvider {
    fun getPlants(): List<Plant> {
        val list: MutableList<Plant> = mutableListOf()

        list.add(
            Plant(
                name = "Plant 1",
                photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
                nextWateringDay = "2023-07-26"
            )
        )

        list.add(
            Plant(
                name = "Plant 2",
                photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
                nextWateringDay = "2023-07-26"
            )
        )

        list.add(
            Plant(
                name = "Plant 3",
                photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
                nextWateringDay = "2023-07-26"
            )
        )

        list.add(
            Plant(
                name = "Plant 1",
                photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
                nextWateringDay = "2023-07-26"
            )
        )

        list.add(
            Plant(
                name = "Plant 4",
                photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
                nextWateringDay = "2023-07-26"
            )
        )

        list.add(
            Plant(
                name = "Plant 5",
                photo = "https://momcrieff.com/wp-content/uploads/2023/01/My-Front-door-planter-3.jpg",
                nextWateringDay = "2023-07-26"
            )
        )
        return list
    }
}