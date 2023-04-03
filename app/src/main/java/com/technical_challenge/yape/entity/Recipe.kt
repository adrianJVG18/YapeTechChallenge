package com.technical_challenge.yape.entity

/**
 * Recipe Entity: represents the core data of a Recipe
 */
data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
)