package com.technical_challenge.yape.domain.interactions

import com.technical_challenge.yape.data.repository.recipe.RecipeRepository
import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * [FetchRecipesUsecase] follows the design pattern "Usecase", where i could to potentially
 * encapsulate any Business related logic for this user interaction.
 *
 * Notice that the param [recipeRepository] type is [RecipeRepository] and not directly an
 * implementation of it, Following Liskov Substitution principle
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
class FetchRecipesUsecase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : FlowUsecase<Nothing, Response<List<RecipeDto>>>() {
    override suspend fun execute(params: Nothing?): Flow<Response<List<RecipeDto>>> = flow {
        recipeRepository.getRecipes().collect { response ->
            when (response) {
                is Response.Success -> {
                    emit(Response.Success(response.data))
                }
                is Response.Failure -> {
                    emit(Response.Failure(response.error, response.errorMessage))
                }
                else -> {} // Do nothing for Response.Loading
            }
        }
    }
}