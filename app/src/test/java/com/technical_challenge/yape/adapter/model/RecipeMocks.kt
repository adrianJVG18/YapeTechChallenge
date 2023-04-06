package com.technical_challenge.yape.adapter.model

import com.technical_challenge.yape.data.repository.recipe.LocationDto
import com.technical_challenge.yape.data.repository.recipe.RecipeDto
import com.technical_challenge.yape.entity.Location
import com.technical_challenge.yape.entity.Recipe
import com.technical_challenge.yape.entity.RecipesResponse

class RecipeMocks {
    companion object {
        const val NAME = "recipe#"
        const val DESCRIPTION = "description#"
        const val IMAGE_URL = "https://any.domain.com/"
        const val LATITUDE = 123456789.0
        const val LONGITUDE = 987654321.0
        const val FAILED_FETCHING_ERROR_MESSAGE = "Failed to fetch recipes"

        private fun createDtoFromId(id: Int): RecipeDto =
            RecipeDto(
                id = "$id",
                name = "$NAME$id",
                description = "$DESCRIPTION$id",
                originLocation = LocationDto(LATITUDE-id, LONGITUDE-id),
                imageUrl = "$IMAGE_URL$id"
            )

        fun getRecipeDtoResponseA() : List<RecipeDto> {
            val list = ArrayList<RecipeDto>()
            for (i in 0..3) {
                list.add(createDtoFromId(i))
            }
            return list
        }

        private fun createRecipeEntityFromId(id: Int): Recipe =
            Recipe(
                id = "$id",
                name = "$NAME$id",
                description = "$DESCRIPTION$id",
                originLocation = Location("${LATITUDE-id}", "${LONGITUDE-id}"),
                imageUrl = "$IMAGE_URL$id"
            )

        fun getRecipeResponseEntity(): RecipesResponse {
            val list = ArrayList<Recipe>()
            for (i in 0..3) {
                list.add(createRecipeEntityFromId(i))
            }
            return RecipesResponse(list)
        }
    }
}