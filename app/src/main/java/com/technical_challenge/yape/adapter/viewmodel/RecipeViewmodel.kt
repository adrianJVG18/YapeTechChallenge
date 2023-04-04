package com.technical_challenge.yape.adapter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technical_challenge.yape.data.repository.Response
import com.technical_challenge.yape.domain.interactions.FetchRecipesUsecase
import com.technical_challenge.yape.framework.adapter.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class RecipeViewmodel @Inject constructor(
    private val fetchRecipesUsecase: FetchRecipesUsecase
) : ViewModel() {

    private val _recipes: MutableStateFlow<Output<List<RecipeItem>>> =
        MutableStateFlow(Output.Loading(false))

    val recipes: StateFlow<Output<List<RecipeItem>>> = _recipes


    fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.emit(Output.Loading(true))
            fetchRecipesUsecase.execute().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _recipes.emit(
                            Output.Success(response.data.map { RecipeItem(it.name, it.imageUrl) })
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
}