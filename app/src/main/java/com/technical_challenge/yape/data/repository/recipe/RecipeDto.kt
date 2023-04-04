package com.technical_challenge.yape.data.repository.recipe

import com.technical_challenge.yape.entity.Recipe

/**
 * [RecipeDto] represents a lightweight Data Transfer Object, with deserialized information
 * the entity []
 */
data class RecipeDto(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val originLocation: String
)

fun Recipe.toDto() = RecipeDto(
    id, name, description?: "", imageUrl?: "", originLocation?: ""
)