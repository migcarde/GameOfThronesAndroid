package com.example.gameofthrones.operations.houses

import com.example.domain.operations.houses.HouseBusiness
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.DORNE
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.IRON_ISLANDS
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.NORTH
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.REACH
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.RIVERLANDS
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.STORMLANDS
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.VALE
import com.example.gameofthrones.operations.houses.HouseViewEntity.Companion.WESTERLANDS
import java.util.*

data class HouseViewEntity(
    val id: String,
    val name: String,
    val region: String,
    val title: String,
    val picture: String?
) {
    companion object {
        const val NORTH = "the north";
        const val VALE = "the vale";
        const val RIVERLANDS = "the riverlands";
        const val IRON_ISLANDS = "iron islands";
        const val WESTERLANDS = "the westerlands";
        const val REACH = "the reach";
        const val DORNE = "dorne";
        const val STORMLANDS = "the stormlands";
    }
}

fun HouseBusiness.toPresentation(): HouseViewEntity {
    val picture = when (region.toLowerCase(Locale.getDefault())) {
        NORTH -> "https://bit.ly/2Gcq0r4"
        VALE -> "https://bit.ly/34FAvws"
        RIVERLANDS, IRON_ISLANDS -> "https://bit.ly/3kJrIiP"
        WESTERLANDS -> "https://bit.ly/2TAzjnO"
        REACH -> "https://bit.ly/2HSCW5N"
        DORNE -> "https://bit.ly/2HOcavT"
        STORMLANDS -> "https://bit.ly/34F2sEC"
        else -> null
    }


    return HouseViewEntity(
        id = id,
        name = name,
        region = region,
        title = title,
        picture = picture
    )
}