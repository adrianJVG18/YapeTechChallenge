package com.technical_challenge.yape.data.retrofit

import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.data.repository.recipe.RecipeRepository
import com.technical_challenge.yape.data.repository.recipe.toDto
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MockableRecipeRepositoryService @Inject constructor(
    private val recipeApi: MockableRecipeApi
) : RecipeRepository {
    override suspend fun getRecipes(): Flow<Response<List<RecipeDto>>> = flow {
        flow {
            emit(Response.Loading(true))
            emit(Response.Success(
                recipeApi.getAllRecipes().recipes.map { it.toDto() }
            ))
        }.catch {
            // TODO catch more specific error causes
            emit(Response.Failure(Exception(it), "Failed to fetch Recipes"))
        }.flowOn(Dispatchers.IO)
    }

}