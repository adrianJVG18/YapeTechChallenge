package com.technical_challenge.yape.data.repository.recipe

import com.technical_challenge.yape.data.repository.Response
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipes(): Flow<Response<List<RecipeDto>>>
}