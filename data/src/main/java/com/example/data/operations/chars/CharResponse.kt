package com.example.data.operations.chars

import com.example.domain.operations.chars.CharBusiness

data class CharResponse(
    val id: String,
    val name: String,
    val gender: String,
    val culture: String? = null,
    val born: String? = null,
    val died: String? = null,
    val titles: List<String>,
    val aliases: List<String>,
    val father: String? = null,
    val mother: String? = null,
    val spouse: String? = null,
    val allegiances: List<String>,
    val playedBy: List<String>
)

fun CharResponse.toDomain() = CharBusiness(
    id,
    name,
    gender,
    culture,
    born,
    died,
    titles,
    aliases,
    father,
    mother,
    spouse,
    allegiances,
    playedBy
)