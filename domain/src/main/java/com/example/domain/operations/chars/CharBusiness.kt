package com.example.domain.operations.chars

data class CharBusiness(
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