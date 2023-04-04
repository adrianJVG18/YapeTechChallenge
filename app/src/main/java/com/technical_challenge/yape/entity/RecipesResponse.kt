package com.technical_challenge.yape.entity

import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    @SerializedName("recipes")
    val recipes: List<Recipe>
)