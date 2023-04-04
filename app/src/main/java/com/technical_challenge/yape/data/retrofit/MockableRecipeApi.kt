package com.technical_challenge.yape.data.retrofit

import com.technical_challenge.yape.entity.RecipesResponse
import retrofit2.http.GET

interface MockableRecipeApi {
    @GET("recipes")
    suspend fun getAllRecipes(): RecipesResponse
}