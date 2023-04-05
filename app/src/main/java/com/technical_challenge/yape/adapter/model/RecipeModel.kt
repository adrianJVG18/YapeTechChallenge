package com.technical_challenge.yape.adapter.model

data class RecipeModel(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val originLocation: LocationModel?
)