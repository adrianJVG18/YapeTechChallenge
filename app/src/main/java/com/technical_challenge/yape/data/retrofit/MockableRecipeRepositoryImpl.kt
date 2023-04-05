package com.technical_challenge.yape.data.retrofit

import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.LocationDto
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.data.repository.recipe.RecipeRepository
import com.technical_challenge.yape.entity.Location
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
class MockableRecipeRepositoryImpl @Inject constructor(
    private val recipeApi: MockableRecipeApi
) : RecipeRepository {
    override suspend fun getRecipes(): Flow<Response<List<RecipeDto>>> = flow {
        emit(Response.Loading(true))
        emit(Response.Success(
            recipeApi.getAllRecipes().recipes.map { recipe ->
                RecipeDto(
                    id = recipe.id,
                    name = recipe.name,
                    description = recipe.description ?: "",
                    imageUrl = recipe.imageUrl ?: "",
                    originLocation = getLocation(recipe.originLocation)
                )
            }
        ))
    }.catch {
        // TODO catch more specific error causes
        emit(Response.Failure(Exception(it), "Failed to fetch Recipes"))
    }.flowOn(Dispatchers.IO)

    private fun getLocation(location: Location?): LocationDto? {
        return if (location == null || location.latitude.isNullOrBlank() || location.longitude.isNullOrBlank())
            null
        else
            try {
                LocationDto(location.latitude.toDouble(), location.longitude.toDouble())
            } catch (e: NumberFormatException) {
                null
            }
    }
}