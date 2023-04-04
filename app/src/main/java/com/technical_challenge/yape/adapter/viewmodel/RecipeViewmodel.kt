package com.technical_challenge.yape.adapter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.domain.interactions.FetchRecipesUsecase
import com.technical_challenge.yape.framework.adapter.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RecipeViewmodel @Inject constructor(
    private val fetchRecipesUsecase: FetchRecipesUsecase
) : ViewModel() {

    private val _recipes: MutableStateFlow<Output<List<RecipeDto>>> =
        MutableStateFlow(Output.Loading(false))

    val recipes: StateFlow<Output<List<RecipeDto>>> = _recipes
    var selectedRecipe: RecipeDto? = null

    fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.emit(Output.Loading(true))
            fetchRecipesUsecase.execute().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _recipes.emit(
                            Output.Success(response.data)
                        )
                    }
                    is Response.Failure -> {
                        _recipes.emit(
                            Output.Failure(response.error, response.errorMessage)
                        )
                    }
                    else -> { } // Do nothing
                }
            }
        }
    }

    fun updateSelectedRecipe(id: String) {
        if (recipes.value is Output.Success) {
            selectedRecipe = (recipes.value as Output.Success).data.find {
                it.id == id
            }
        }
    }
}