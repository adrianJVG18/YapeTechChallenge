package com.technical_challenge.yape.adapter.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technical_challenge.yape.adapter.errors.EmptyResponseError
import com.technical_challenge.yape.adapter.errors.ErrorMessages
import com.technical_challenge.yape.adapter.model.LocationModel
import com.technical_challenge.yape.adapter.model.Output
import com.technical_challenge.yape.adapter.model.RecipeModel
import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.LocationDto
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.domain.interactions.FetchRecipesUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RecipeViewmodel @Inject constructor(
    private val fetchRecipesUsecase: FetchRecipesUsecase
) : ViewModel() {

    private val _recipes: MutableStateFlow<Output<List<RecipeModel>>> =
        MutableStateFlow(Output.Loading(false))
    val recipes: StateFlow<Output<List<RecipeModel>>> = _recipes

    var selectedRecipe: RecipeModel? = null
    val hasLocation: Boolean
        get() = selectedRecipe?.originLocation != null


    fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.emit(Output.Loading(true))
            fetchRecipesUsecase.execute().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _recipes.emit(
                            if (response.data.isEmpty())
                                Output.Failure(EmptyResponseError(), ErrorMessages.EMPTY_RESPONSE)
                            else
                                Output.Success(response.data.map { convertToRecipeModel(it) })
                        )
                    }
                    is Response.Failure -> {
                        _recipes.emit(
                            Output.Failure(response.error, response.errorMessage)
                        )
                    }
                    else -> {} // Do nothing
                }
            }
        }
    }

    fun updateSelectedRecipe(id: String) {
        selectedRecipe = (recipes.value as Output.Success).data.find {
            it.id == id
        }
    }

    private fun getLocationModel(locationDto: LocationDto?): LocationModel? =
        if (locationDto == null) null
        else LocationModel(locationDto.latitude, locationDto.longitude)

    @VisibleForTesting
    fun convertToRecipeModel(recipeDto: RecipeDto): RecipeModel =
        with(recipeDto) {
            RecipeModel(
                id = id,
                name = name,
                description = description,
                imageUrl = imageUrl,
                originLocation = getLocationModel(originLocation)
            )
        }
}