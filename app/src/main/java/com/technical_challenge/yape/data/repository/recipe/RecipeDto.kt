package com.technical_challenge.yape.data.repository.recipe

/**
 * [RecipeDto] represents a lightweight Data Transfer Object, with deserialized information
 * the entity []
 */
data class RecipeDto(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val originLocation: LocationDto?
)
