package com.example.gameofthrones.operations.chars

import com.example.domain.operations.chars.CharBusiness

data class CharViewEntity(
    val id: String,
    val name: String,
    val gender: String? = null,
    val culture: String? = null,
    val born: String? = null,
    val died: String? = null,
    val titles: String? = null,
    val aliases: String? = null,
    val father: String? = null,
    val mother: String? = null,
    val spouse: String? = null,
    val allegiances: String? = null,
    val playedBy: String? = null
)

fun CharBusiness.toPresentation(): CharViewEntity {
    var titlesValue = ""

    titles.forEachIndexed { index, author ->
        titlesValue = when (index == 0) {
            true -> String.format("%s", author)
            false -> String.format("%s, %s", titlesValue, author)
        }
    }

    var aliasesValue = ""

    aliases.forEachIndexed { index, alias ->
        aliasesValue = when (index == 0) {
            true -> String.format("%s", alias)
            false -> String.format("%s, %s", aliasesValue, alias)
        }
    }

    var allegiancesValue = ""

    aliases.forEachIndexed { index, allegiance ->
        allegiancesValue = when (index == 0) {
            true -> String.format("%s", allegiance)
            false -> String.format("%s, %s", allegiancesValue, allegiance)
        }
    }

    var playedByValue = ""

    aliases.forEachIndexed { index, playedBy ->
        playedByValue = when (index == 0) {
            true -> String.format("%s", playedBy)
            false -> String.format("%s, %s", playedByValue, playedBy)
        }
    }

    return CharViewEntity(
        id,
        name,
        gender.ifBlank { null },
        culture?.ifBlank { null },
        born?.ifBlank { null },
        died?.ifBlank { null },
        titlesValue.ifBlank { null },
        aliasesValue.ifBlank { null },
        father?.ifBlank { null },
        mother?.ifBlank { null },
        spouse?.ifBlank { null },
        allegiancesValue.ifBlank { null },
        playedByValue.ifBlank { null }
    )
}